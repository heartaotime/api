package com.open.custom.api.test;

import com.open.custom.api.domain.request.QryPicRequest;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ttt {

    public static void main(String[] args) throws Exception {
        QryPicRequest qryPicRequest = new QryPicRequest();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        List<String> companyNos = new ArrayList<>();
        companyNos.add("1");
        companyNos.add("2");
        companyNos.add("3");
        for (String companyNoItem : companyNos) {
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    QryPicRequest qryPicRequest = new QryPicRequest();
                    qryPicRequest.setPicOrigin(companyNoItem);
                    try {
                        test(qryPicRequest);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//        for (final  i = 0; i < 2; i++) {
//            final int index = i;
////            QryPicRequest qryPicRequest = new QryPicRequest();
////            qryPicRequest.setPicOrigin(index + "");
//            fixedThreadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//
////                        Thread.sleep(2000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
        fixedThreadPool.shutdown();
        while (!fixedThreadPool.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("---continue");
        }

        System.out.println("---end");


    }

    private static void test(String q) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(q);
    }

    private static void test(QryPicRequest qryPicRequest) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(qryPicRequest.getPicOrigin());
    }

//    public static void main(String[] args) {
//        List list = new ArrayList();
//        list.add(null);
//        System.out.println(list.size());
//    }
}
