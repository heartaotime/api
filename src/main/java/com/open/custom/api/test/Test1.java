package com.open.custom.api.test;

import cn.hutool.aop.aspects.SimpleAspect;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.redisson.misc.Hash;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1  {


    public static void main(String[] args) throws Exception {
        ExcelWriter writer = ExcelUtil.getWriter();

        List<User> list = new ArrayList<>();
        list.add(new User("你"));
        list.add(new User("是"));
        list.add(new User("谁"));
        writer.addHeaderAlias("name", "名字");

//        writer.merge(2, "合并");
        writer.write(list, true);
//        FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\heart\\Desktop\\tmp\\1.xlsx"));
        writer.flush(new File("C:\\Users\\heart\\Desktop\\tmp\\1.xlsx"));
        writer.close();


    }
}
