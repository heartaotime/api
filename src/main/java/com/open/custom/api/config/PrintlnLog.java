package com.open.custom.api.config;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface PrintlnLog {

    /**
     * Description: 自定义日志描述信息
     * Author: huxintao
     * Date: 2020-12-14
     *
     * @param
     * @Return java.lang.String
     */
    String description() default "";
}
