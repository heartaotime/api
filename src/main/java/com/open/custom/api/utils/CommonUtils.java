package com.open.custom.api.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;

public class CommonUtils {

//    public static void convertList(List orig, List dest, Class destClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
//        if (!CollectionUtils.isEmpty(orig)) {
//            for (Object item : orig) {
//                Object destObj = destClass.newInstance();
//                BeanUtils.copyProperties(destObj, item);
//                dest.add(destObj);
//            }
//        }
//    }

    public static <T> T map2Bean(Map map, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    public static Map obj2Map(Object obj) {
        return JSON.parseObject(JSON.toJSONString(obj), Map.class);
    }

    public static String listToStrWithComma(List<String> needChange) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < needChange.size(); i++) {
            String item = needChange.get(i);
            sb.append(" '").append(item).append("',");
        }
        String s = sb.toString();
        s = s.substring(0, s.length() - 1);
        return s;
    }


}
