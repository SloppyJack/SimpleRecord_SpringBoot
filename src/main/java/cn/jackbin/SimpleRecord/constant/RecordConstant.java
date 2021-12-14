package cn.jackbin.SimpleRecord.constant;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.constant
 * @date: 2020/10/12 21:51
 **/
public class RecordConstant {
    public static final String EXPEND_RECORD_TYPE = "expendType";

    public static final String INCOME_RECORD_TYPE = "incomeType";

    public static final String RECORD_TYPE = "recordType";

    public static final String PAYMENT_ACCOUNT = "payment"; // 应收应付账户类型

    public static final String BXK = "报销款";

    /**
     * 用户默认
     */
    public static final int USER_DEFAULT = 1;

    /**
     * 非用户默认
     */
    public static final int NOT_USER_DEFAULT = 2;

    /**
     * 属于
     */
    public static final int BUSINESS_YES = 1;

    /**
     * 不属于
     */
    public static final int BUSINESS_NOT = 2;

    /**
     * 非报销
     */
    public static final int NOT_RECOVERABLE = 1;

    /**
     * 待报销
     */
    public static final int TO_RECOVERABLE = 2;

    /**
     * 已报销
     */
    public static final int IS_RECOVERABLE = 3;

}
