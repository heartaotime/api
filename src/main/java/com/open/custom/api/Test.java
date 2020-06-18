package com.open.custom.api;

import com.google.gson.Gson;
import com.open.custom.api.model.OpenStaticData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {

    public static void main(String[] args) {
//        String str = "https://cn.bing.com/th?id=OHR.BorrowingDays_ZH-CN3558219803_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp";
//        str = str.substring(0, str.indexOf("&"));
//
//        String s1920 = str;
//        String s1366 = str.replaceAll("1920x1080", "1366x768");
//        String s1080 = str.replaceAll("1920x1080", "1080x1920");
//        String uhd = str.replaceAll("1920x1080", "UHD");
//
//        System.out.println(s1920);
//        System.out.println(s1366);
//        System.out.println(s1080);
//        System.out.println(uhd);

        OpenStaticData t1 = new OpenStaticData();
        t1.setSort(1);

        OpenStaticData t2 = new OpenStaticData();
        t2.setSort(2);

        List<OpenStaticData> openStaticData = new ArrayList<>();

        openStaticData.add(t1);
        openStaticData.add(t2);
        Collections.sort(openStaticData, new Comparator<OpenStaticData>() {
            @Override
            public int compare(OpenStaticData o1, OpenStaticData o2) {
                return (o1.getSort() - o2.getSort());
            }
        });

//        openStaticData.stream().sorted(new Comparator<OpenStaticData>() {
//            @Override
//            public int compare(OpenStaticData o1, OpenStaticData o2) {
//                return -( o1.getSort() - o2.getSort());
//            }
//        });

        System.out.println(new Gson().toJson(openStaticData));
    }
}
