package com.open.custom.api.test;

import cn.hutool.core.date.DateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.open.custom.api.model.OpenStaticData;
import io.swagger.models.auth.In;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {

    private boolean isEqual(Object o1, Object o2) {

        int i1 = o1.hashCode();
        int i2 = o2.hashCode();
//        if (o1 instanceof Byte) {
//            return String.valueOf(o1).equals(String.valueOf(o2));
//        } else if (o1 instanceof Short) {
//            return (short) o1 == (short) Short.parseShort(String.valueOf(o2));
//        } else if (o1 instanceof Integer) {
//            return (int) o1 == (int) Integer.parseInt(String.valueOf(o2));
//        } else if (o1 instanceof Long) {
//            return (long) o1 == (long) Long.parseLong(String.valueOf(o2));
//        } else if (o1 instanceof Float) {
//            return (float) o1 == (float) Float.parseFloat(String.valueOf(o2));
//        } else if (o1 instanceof Double) {
//            return (double) o1 == (double) Double.parseDouble(String.valueOf(o2));
//        } else if (o1 instanceof Boolean) {
//            return String.valueOf(o1).equals(String.valueOf(o2));
//        } else if (o1 instanceof Character) {
//            return String.valueOf(o1).equals(String.valueOf(o2));
//        } else if (o1 instanceof Date) {
//            return o1.equals(o2);
//        } else if (o1 instanceof String) {
//            return String.valueOf(o1).equals(String.valueOf(o2));
//        }

        if (o1 instanceof BigDecimal) {
            return ((BigDecimal) o1).doubleValue() == ((BigDecimal) o2).doubleValue();
        } else {
            return String.valueOf(o1).equals(String.valueOf(o2));
        }
    }

    class in {
        private Date date;

        private int a;

    }

    public static void main(String[] args) throws Exception {


        Date[] d = new Date[12];
        Date[] d1 = new Date[12];
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < 12; i++) {
            c.add(Calendar.MONTH, -1);
            d[i] = c.getTime();
            System.out.println(new SimpleDateFormat("yyyyMM").format(c.getTime()));
        }

        for (int i = 12; i < 24; i++) {
            c.add(Calendar.MONTH, -1);
            d1[i - 12] = c.getTime();
        }


//        for (int i = 0; i < 12; i++) {
//            c.add(Calendar.MONTH, -1);
//
//        }

//        String now = DateUtil.now();
//        System.out.println(now);


//        Gson gson = new Gson();
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
//        gson = gsonBuilder.create();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("test", 1.2);
//        String s = gson.toJson(map);
//        Map<String, Object> map1 = gson.fromJson(s, Map.class);
//        System.out.println(map1.toString());
//        Set<Map.Entry<String, Object>> entries = map1.entrySet();
//        for (Map.Entry<String, Object> entry : entries) {
//            String key = entry.getKey();
////            if ("java.lang.Integer".equals(orgColType)) {
////
////            }
//            double v = Double.parseDouble(entry.getValue() + "");
//            BigDecimal bg = new BigDecimal(v);
//            entry.setValue(bg.intValue());
//
//        }
//        System.out.println(map1.toString());
//
//        Class<in> inClass = in.class;
//        Field date = inClass.getDeclaredField("date");
////        date.setAccessible(true);
//        Type genericType = date.getGenericType();
//        String typeName = genericType.getTypeName();
//        System.out.println(typeName);
//
//
//        System.out.println("河南省郑州市惠济区文化北路与开元路交汇处向西300米惠济万达广场2楼2006#热风".equals("河南省郑州市惠济区文化北路与开元路交汇处向西300米惠济万达广场2楼2006#热风"));
//
//
//        Test test = new Test();
//        Date now = new Date();
//        System.out.println(now.getTime());
//        long a = 1606728462217l;
//        long b = 1606728462218l;
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(a)));
//        System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(b)));
//        Object o1 = new BigDecimal("1.0");
//        Object o2 = new BigDecimal("1.00");
//        System.out.println(String.valueOf(o1));
//        System.out.println(String.valueOf(o2));
////        System.out.println(o2);
//        System.out.println(test.isEqual(o1, o2));

//        String str = "";
//        int ww = 12;
//
//        Date now = new Date();
//        Object o1 = (byte) 123;
//        Object o2 = now;
//
//        int i1 = o1.hashCode();
//        int i2 = o2.hashCode();
//
//
//        System.out.println(o1 instanceof Byte);
//        System.out.println(i1 == i2);
//        System.out.println(o1 == o2);


//        System.out.println(ww instanceof Integer);
//        List<Integer> list1 = new ArrayList<>();
//        list1.add(1);
//        list1.add(2);
////        list1.add(3);
//        List<Integer> list2 = new ArrayList<>();
//        list2.add(1);
//        list2.add(2);
//        list2.add(4);
//
//
//        // 创建集合
//        Collection<Integer> realA = new ArrayList<Integer>(list1);
//        realA.retainAll(new ArrayList<Integer>(list2));
//        System.out.println("交集");
//        for(Integer item: realA) {
//            System.out.println(item);
//        }
//
//
//        Collection<Integer> realB = new ArrayList<Integer>(list1);
//        realB.removeAll(realA);
//        System.out.println("list1 在 list2 中没有的");
//        for(Integer item: realB) {
//            System.out.println(item);
//        }
//
//        Collection<Integer> realC = new ArrayList<Integer>(list2);
//        realC.removeAll(realA);
//        System.out.println("list2在 list1 中没有的");
//        for(Integer item: realC) {
//            System.out.println(item);
//        }
//        HashMap map = new HashMap();
//        Object val1 = map.put("test", "value11111");
//        System.out.println(val1);
//        Object val2 = map.put("test", "value22");
//        System.out.println(val2);

//        Workbook workbook = new XSSFWorkbook(new FileInputStream(
//                new File("E:\\Download\\HW02-新店盈利测算汇总表-热风-老店（续约、移扩等）-沈阳大悦城店 (t).xlsx")));
////        int numberOfSheets = workbook.getNumberOfSheets();
////        for (int i = 0; i < numberOfSheets; i++) {
////            Sheet sheet = workbook.getSheetAt(i);
////            for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) { // 获取每行
////                XSSFRow row = (XSSFRow) sheet.getRow(j);
////                if (row != null) {
////                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
////                    for (int k = 0; k < physicalNumberOfCells; k++) { // 获取每个单元格
////                        Cell cell = row.getCell(k);
////                        if (cell == null) {
////                            continue;
////                        }
////                        switch (cell.getCellType()) {
////                            case Cell.CELL_TYPE_STRING:
////                                break;
////                            case Cell.CELL_TYPE_NUMERIC:
////                                break;
////                            case Cell.CELL_TYPE_BOOLEAN:
////                                break;
////                            case Cell.CELL_TYPE_FORMULA:
////                                System.out.println(cell.getCellFormula());
////                                break;
////                            default:
////                                break;
////                        }
////                    }
////                }
////            }
////        }
////         HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
////        workbook.setForceFormulaRecalculation(true);
//        //XSSFFormulaEvaluator.evaluateAllFormulaCells((XSSFWorkbook）book）;
////        workbook.getCreationHelper().createFormulaEvaluator().evaluateAll();
//        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//        evaluator.setDebugEvaluationOutputForNextEval(true);
//
//        System.out.println(workbook.getSheetName(0));
//        Sheet firstSheet = workbook.getSheetAt(0);
//        Row r2 = firstSheet.getRow(41);
//        Cell cell = r2.getCell(1);
//        int cellType = cell.getCellType();
//
//        String cellFormula = cell.getCellFormula();
//        System.out.println(cellFormula);
//        cell.setCellFormula(cellFormula);
//        evaluator.evaluateInCell(cell);
////        String cellValue = cell.getStringCellValue();
//        String cellValue = cell.getNumericCellValue() + "";
//        System.out.println(cellValue);
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
