package com.open.custom.api.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WordUtil {


    private static DecimalFormat df0 = new DecimalFormat("#0");
    private static DecimalFormat df1 = new DecimalFormat("#0.0");
    private static DecimalFormat df2 = new DecimalFormat("#0.00");

    private static DateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd");
    private static DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");


    public static void generateContractWord(Map<String, Object> param, OutputStream dest) throws Exception {
        InputStream is = WordUtil.class.getResourceAsStream("/templates/rentContract.docx");
        XWPFDocument doc = new XWPFDocument(is);
        is.close();
        if (param != null && param.size() > 0) {
            // 处理段落
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            processParagraphs(paragraphList, param);
        }

        List<XWPFTable> tables = doc.getTables();
        if (!CollectionUtils.isEmpty(tables)) {

            Object tab1 = param.get("tab1");
            if (tab1 != null) {
                XWPFTable xwpfTable = tables.get(0);

//                List<ContractBuilding> contractBuildingList = (List) tab1;
//                for (int i = 0; i < contractBuildingList.size(); i++) {
//                    ContractBuilding contractBuilding = contractBuildingList.get(i);
//
//                    int numberOfRows = xwpfTable.getNumberOfRows();
//                    XWPFTableRow targetRow = xwpfTable.insertNewTableRow(numberOfRows);
//
//                    XWPFTableCell xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(contractBuilding.getBuilding());
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(contractBuilding.getFlor());
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(contractBuilding.getRoomNumber());
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(getStrByBigDecimal(contractBuilding.getArea()));
//                }
//
//
//                xwpfTable = tables.get(1);
//                for (int i = 0; i < contractBuildingList.size(); i++) {
//                    ContractBuilding contractBuilding = contractBuildingList.get(i);
//                    int numberOfRows = xwpfTable.getNumberOfRows();
//                    XWPFTableRow targetRow = xwpfTable.insertNewTableRow(numberOfRows);
//
//                    XWPFTableCell xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(contractBuilding.getBuilding());
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(contractBuilding.getFlor());
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(contractBuilding.getRoomNumber());
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(getStrByBigDecimal(contractBuilding.getArea()));
//
//                    xwpfTableCell = targetRow.addNewTableCell();
//                    xwpfTableCell.setText(getStrByBigDecimal(contractBuilding.getRentDay()));
//                }

            }


            Object tab2 = param.get("tab2");
            if (tab2 != null) {
                XWPFTable xwpfTable = tables.get(2);

//                List<Rent> rentList = (List) tab2;
//                for (int i = 0; i < rentList.size(); i++) {
//                    Rent rent = rentList.get(i);
//
//                    String type = rent.getType();
//                    if (!StringUtils.isEmpty(type) && "房租".equals(type)) {
//                        int numberOfRows = xwpfTable.getNumberOfRows();
//                        XWPFTableRow targetRow = xwpfTable.insertNewTableRow(numberOfRows);
//
//                        XWPFTableCell xwpfTableCell = targetRow.addNewTableCell();
//                        xwpfTableCell.setText(rent.getYear() + "");
//
//                        xwpfTableCell = targetRow.addNewTableCell();
//                        xwpfTableCell.setText(rent.getName() + "");
//
//                        xwpfTableCell = targetRow.addNewTableCell();
//                        xwpfTableCell.setText(type);
//
//                        xwpfTableCell = targetRow.addNewTableCell();
//                        Date yjStarttime = rent.getYjStarttime();
//                        Date yjEndtime = rent.getYjEndtime();
//                        String yjStarttimeStr = "";
//                        String yjEndtimeStr = "";
//                        if (yjStarttime != null) {
//                            yjStarttimeStr = dateFormat2.format(yjStarttime);
//                        }
//                        if (yjEndtime != null) {
//                            yjEndtimeStr = dateFormat2.format(yjEndtime);
//                        }
//                        xwpfTableCell.setText(yjStarttimeStr + "-" + yjEndtimeStr);
//
//                        xwpfTableCell = targetRow.addNewTableCell();
//                        xwpfTableCell.setText(getStrByBigDecimal(rent.getYjMoney()));
//                    }
//                }
            }
        }
        doc.write(dest);
    }

    private static String getStrByBigDecimal(BigDecimal val) {
        if (val == null) {
            return null;
        }
        return df2.format(val);
    }

    /**
     * insertRow 在word表格中指定位置插入一行，并将某一行的样式复制到新增行
     *
     * @param copyrowIndex 需要复制的行位置
     * @param newrowIndex  需要新增一行的位置
     */
    public static void insertRow(XWPFTable table, int copyrowIndex, int newrowIndex) {
        // 在表格中指定的位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(newrowIndex);
        // 获取需要复制行对象
        XWPFTableRow copyRow = table.getRow(copyrowIndex);
        //复制行对象
        targetRow.getCtRow().setTrPr(copyRow.getCtRow().getTrPr());
        //或许需要复制的行的列
        List<XWPFTableCell> copyCells = copyRow.getTableCells();
        //复制列对象
        XWPFTableCell targetCell = null;
        for (int i = 0; i < copyCells.size(); i++) {
            XWPFTableCell copyCell = copyCells.get(i);
            targetCell = targetRow.addNewTableCell();
            targetCell.getCTTc().setTcPr(copyCell.getCTTc().getTcPr());
            if (copyCell.getParagraphs() != null && copyCell.getParagraphs().size() > 0) {
                targetCell.getParagraphs().get(0).getCTP().setPPr(copyCell.getParagraphs().get(0).getCTP().getPPr());
                if (copyCell.getParagraphs().get(0).getRuns() != null
                        && copyCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    cellR.setBold(copyCell.getParagraphs().get(0).getRuns().get(0).isBold());
                }
            }
        }

    }


    public static void generateWord(Map<String, Object> param, String fileSrc, OutputStream dest) throws Exception {
        InputStream is = WordUtil.class.getResourceAsStream(fileSrc);
        XWPFDocument doc = new XWPFDocument(is);
        is.close();
        if (param != null && param.size() > 0) {
            // 处理段落
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            processParagraphs(paragraphList, param);

//            replaceInPara(doc, param);
        }
        doc.write(dest);
    }

    public static void generateWord(Map<String, Object> param, String fileSrc, String fileDest) {
        XWPFDocument doc = null;
//        OPCPackage pack = null;
        try {
//            pack = POIXMLDocument.openPackage(fileSrc);
//            doc = new XWPFDocument(pack);

            InputStream is = new FileInputStream(fileSrc);
            doc = new XWPFDocument(is);
            if (param != null && param.size() > 0) {
                // 处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param);
            }

            FileOutputStream fopts = new FileOutputStream(fileDest);
            ;
            doc.write(fopts);
            fopts.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeStream(FileOutputStream fopts) {
        try {
            fopts.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
//                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理段落,替换关键字
     *
     * @param paragraphList
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param)
            throws InvalidFormatException, FileNotFoundException {
        if (paragraphList != null && paragraphList.size() > 0) {
            // 遍历所有段落
            for (XWPFParagraph paragraph : paragraphList) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    XWPFRun run = runs.get(i);
                    String runText = run.getText(0);
//                    System.out.println("text=" + runText);
                    if (runText != null) {
                        boolean isSetText = false;
                        for (Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (runText.indexOf(key) != -1) {// 在配置文件中有这个关键字对应的键
                                isSetText = true;
                                Object value = entry.getValue();
//                                if (value instanceof String) {// 文本替换
////                                    System.out.println("key==" + key);
//                                    if (runText.contains(key)) {
//                                        runText = runText.replace(key, value.toString());
//                                        break;
//                                    }
//                                }

                                if (runText.contains(key)) {
                                    if (StringUtils.isEmpty(value)) {
                                        value = "        ";
                                    }
                                    runText = runText.replace(key, value.toString());
                                    break;
                                }
//                                // 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
//                                // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
//                                paragraph.removeRun(i);
//                                if(runText.equals("null")){
//                                    runText="";
//                                }
//                                paragraph.insertNewRun(i).setText(runText);

                            }
                        }
                        if (isSetText) {
                            run.setText(runText, 0);
                        }
                    }
                }
            }
        }
    }


    /**
     * 替换段落里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        for (XWPFParagraph para : doc.getParagraphs()) {
            replaceInPara(para, params);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para   要替换的段落
     * @param params 参数
     */
    public static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        replaceText(para);//如果para拆分的不对，则用这个方法修改成正确的
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
//                String runText = run.toString();
                String runText = run.getText(0);
                System.out.println("runText=" + runText);
                if (StringUtils.isEmpty(runText) || runText == "null") {
                    continue;
                }
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    if (StringUtils.isEmpty(runText) || runText == "null") {
                        continue;
                    }
                    run.setText(runText, 0);

                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
//                    para.removeRun(i);
//                    if (StringUtils.isEmpty(runText) || runText == "null") {
//                        runText = "        ";
//                    }
//                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
     * 合并runs中的内容
     *
     * @param runs
     * @return
     */
    public static List<XWPFRun> replaceText(XWPFParagraph para) {
        List<XWPFRun> runs = para.getRuns();
        String str = "";
        boolean flag = false;
        for (int i = 0; i < runs.size(); i++) {
            XWPFRun run = runs.get(i);
            String runText = run.toString();
            if (flag || runText.equals("${")) {

                str = str + runText;
                flag = true;
                para.removeRun(i);
                if (runText.equals("}")) {
                    flag = false;
                    para.insertNewRun(i).setText(str);
                    str = "";
                }
                i--;
            }

        }

        return runs;
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc    要替换的文档
     * @param params 参数
     */
    public static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    public static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
