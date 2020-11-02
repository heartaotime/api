package com.open.custom.api;

import com.google.gson.Gson;
import com.open.custom.api.model.OpenStaticData;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {


    public static void main(String[] args) throws Exception {
        Workbook workbook = new XSSFWorkbook(new FileInputStream(
                new File("E:\\Download\\HW02-新店盈利测算汇总表-热风-老店（续约、移扩等）-沈阳大悦城店 (t).xlsx")));
//        int numberOfSheets = workbook.getNumberOfSheets();
//        for (int i = 0; i < numberOfSheets; i++) {
//            Sheet sheet = workbook.getSheetAt(i);
//            for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) { // 获取每行
//                XSSFRow row = (XSSFRow) sheet.getRow(j);
//                if (row != null) {
//                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
//                    for (int k = 0; k < physicalNumberOfCells; k++) { // 获取每个单元格
//                        Cell cell = row.getCell(k);
//                        if (cell == null) {
//                            continue;
//                        }
//                        switch (cell.getCellType()) {
//                            case Cell.CELL_TYPE_STRING:
//                                break;
//                            case Cell.CELL_TYPE_NUMERIC:
//                                break;
//                            case Cell.CELL_TYPE_BOOLEAN:
//                                break;
//                            case Cell.CELL_TYPE_FORMULA:
//                                System.out.println(cell.getCellFormula());
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                }
//            }
//        }
//         HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
//        workbook.setForceFormulaRecalculation(true);
        //XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook）book）;
//        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.setDebugEvaluationOutputForNextEval(true);

        System.out.println(workbook.getSheetName(0));
        Sheet firstSheet = workbook.getSheetAt(0);
        Row r2 = firstSheet.getRow(41);
        Cell cell = r2.getCell(1);
        int cellType = cell.getCellType();

        String cellFormula = cell.getCellFormula();
        System.out.println(cellFormula);
        cell.setCellFormula(cellFormula);
        evaluator.evaluateInCell(cell);
//        String cellValue = cell.getStringCellValue();
        String cellValue = cell.getNumericCellValue() + "";
        System.out.println(cellValue);
    }


//    public static void main(String[] args) {
////        String str = "https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";
////        str = str.substring(0, str.indexOf("&"));
////
////        String s1920 = str;
////        String s1366 = str.replaceAll("1920x1080", "1366x768");
////        String s1080 = str.replaceAll("1920x1080", "1080x1920");
////        String uhd = str.replaceAll("1920x1080", "UHD");
////
////        System.out.println(s1920);
////        System.out.println(s1366);
////        System.out.println(s1080);
////        System.out.println(uhd);
//
//        OpenStaticData t1 = new OpenStaticData();
//        t1.setSort(1);
//
//        OpenStaticData t2 = new OpenStaticData();
//        t2.setSort(2);
//
//        List<OpenStaticData> openStaticData = new ArrayList<>();
//
//        openStaticData.add(t1);
//        openStaticData.add(t2);
//        Collections.sort(openStaticData, new Comparator<OpenStaticData>() {
//            @Override
//            public int compare(OpenStaticData o1, OpenStaticData o2) {
//                return (o1.getSort() - o2.getSort());
//            }
//        });
//
////        openStaticData.stream().sorted(new Comparator<OpenStaticData>() {
////            @Override
////            public int compare(OpenStaticData o1, OpenStaticData o2) {
////                return -( o1.getSort() - o2.getSort());
////            }
////        });
//
//        System.out.println(new Gson().toJson(openStaticData));
//    }
}
