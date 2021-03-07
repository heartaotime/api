package com.open.custom.api.utils;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class ExportExcelUtils {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 显示的导出表的标题
    private String title;
    // 导出表的列名
    private LinkedHashMap<String, String> titleMap;
    private List dataList = new ArrayList<>();

    // 构造函数，传入要导出的数据
    public ExportExcelUtils(String title, LinkedHashMap<String, String> titleMap, List dataList) {
        this.dataList = dataList;
        this.titleMap = titleMap;
        this.title = title;
    }


    // 导出数据
    public void export(OutputStream out) throws Exception {
        try {

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(title);

            // 产生表格标题行
            XSSFRow rowm = sheet.createRow(0);
            XSSFCell cellTitle = rowm.createCell(0);


            //sheet样式定义【】
            XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            XSSFCellStyle style = this.getStyle(workbook);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, (titleMap.size() - 1)));
            cellTitle.setCellStyle(columnTopStyle);
            cellTitle.setCellValue(title);

            // 定义所需列数
            int columnNum = titleMap.size();
            XSSFRow rowRowName = sheet.createRow(1);

            // 将列头设置到sheet的单元格中
            Set<Map.Entry<String, String>> entries = titleMap.entrySet();
            int n = -1;
            for (Map.Entry<String, String> entry : entries) {
                n += 1;
                String key = entry.getKey();
                String value = entry.getValue();
                XSSFCell cellRowName = rowRowName.createCell(n);
                cellRowName.setCellType(CellType.STRING);
                XSSFRichTextString text = new XSSFRichTextString(value);
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(style);
            }

            // 将查询到的数据设置到sheet对应的单元格中

            for (int i = 0; i < dataList.size(); i++) {
                Object obj = dataList.get(i);// 遍历每个对象
                XSSFRow row = sheet.createRow(i + 2);// 创建所需的行数

                //利用反射获取所有字段
                Field[] fields = obj.getClass().getDeclaredFields();
                int j = -1;
                for (Map.Entry<String, String> entry : entries) {
                    j += 1;

                    // 创建列单元格
                    XSSFCell cell = row.createCell(j, CellType.STRING);
                    cell.setCellStyle(style);

                    String key = entry.getKey();
                    boolean hasKey = false;
                    for (Field field : fields) {
                        //  设置字段可见性
                        field.setAccessible(true);
                        if (!key.equals(field.getName())) {
                            continue;
                        }
                        hasKey = true;
                        Object fieldVal = field.get(obj);
                        if (fieldVal == null) {
                            cell.setCellValue("-");
                            continue;
                        }

                        String writerVal = fieldVal.toString();
                        // 获取字段枚举值转换
//                        if (enumsMap != null && enumsMap.containsKey(key)) {
//                            List<Map<String, String>> enumsList = enumsMap.get(key);
//                            if (!CollectionUtils.isEmpty(enumsList)) {
//                                for (Map<String, String> item : enumsList) {
//                                    String value = item.get("value");
//                                    String name = item.get("name");
//                                    if (value.equals(fieldVal.toString())) {
//                                        writerVal = name;
//                                        break;
//                                    }
//                                }
//                            }
//                        }

                        // 如果是时间则进行格式化
                        Class<?> type = field.getType();
                        String typeName = type.getName();
                        if ("java.util.Date".equals(typeName)) {
                            writerVal = df.format((Date) fieldVal);
                        }
                        if (StringUtils.isEmpty(writerVal)) {
                            writerVal = "-";
                        }
                        cell.setCellValue(writerVal);
                        continue;
                    }
                    if (!hasKey) {
                        log.error("{} 未发现该字段", key);
                        cell.setCellValue("");
                        continue;
                    }
                }

            }

            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    XSSFRow currentRow;
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        XSSFCell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == CellType.STRING) {
                            int length = currentCell.getStringCellValue().getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
//                if (colNum == 0) {
//                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
//                } else {
//                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
//                }

                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }

            if (workbook != null) {
                try {
                    workbook.write(out);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }

        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    /*
     * 列头单元格样式
     */
    public XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();

        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setBold(true);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置低边框
        style.setBorderBottom(BorderStyle.THIN);
        // 设置低边框颜色
        XSSFColor blackColor = new XSSFColor(new Color(0,0,0));
        style.setBottomBorderColor(blackColor);
        // 设置右边框
        style.setBorderRight(BorderStyle.THIN);
        // 设置顶边框
        style.setTopBorderColor(blackColor);
        // 设置顶边框颜色
        style.setTopBorderColor(blackColor);
        // 在样式中应用设置的字体
        style.setFont(font);
        // 设置自动换行
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐；
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;

    }

    public XSSFCellStyle getStyle(XSSFWorkbook workbook) {
        // 设置字体
        XSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 字体加粗
//        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setBold(true);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        XSSFColor blackColor = new XSSFColor(new Color(0,0,0));
        style.setBottomBorderColor(blackColor);
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(blackColor);
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(blackColor);
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(blackColor);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
}
