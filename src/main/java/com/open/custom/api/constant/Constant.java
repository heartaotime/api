package com.open.custom.api.constant;

public class Constant {

    public static interface RSP_TYPE {
        static final String FAIL = "FAIL"; // 失败
        static final String SUCCESS = "SUCCESS"; // 成功
        static final String WARNING = "WARNING"; // 警告
    }

    public static interface RSP_CODE {
        static final String FAIL = "-999";
        static final String SUCCESS = "0";
    }

    public static interface RSP_MSG {
        static final String SUCCESS = "成功"; // 失败
        static final String FAIL = "失败"; // 成功
    }


    /**
     * Description: 数据有效性
     * Author: huxintao
     */
    public static interface RECORD_STATE {
        static final String Y = "Y"; // 有效
        static final String N = "N"; // 无效
    }

    /**
     * Description: PAGE_SIZE
     * Author: huxintao
     */
    public static interface PAGE_SIZE {
        static final int INT_10000 = 10000;
    }


    public static interface NEED {
        static final String YES = "1";
        static final String NO = "0";
    }

}
