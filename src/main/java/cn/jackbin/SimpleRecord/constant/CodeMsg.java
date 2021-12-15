package cn.jackbin.SimpleRecord.constant;

/**
 * 全局错误码
 **/
public enum CodeMsg {
    
    // 按照模块定义CodeMsg
    // 成功
    SUCCESS(0,"success"),
    // 通用异常
    ERROR(-1,"接口调用异常"),
    FAILED(100,"接口调用失败"),
    SERVER_EXCEPTION(101,"服务端异常"),
    JWT_EXCEPTION(102,"JWT校验异常"),
    WITHOUT_PERMISSION(103, "权限不足，请联系管理员"),
    TOKEN_EXPIRED(104, "token失效"),



    // 通用业务 格式500 xxx
    BUSINESS_ERROR(500000,"业务异常"),
    ONLINE_USER_OVER(500003,"在线用户数超出允许登录的最大用户限制。"),
    NOT_FIND_DATA(500005,"查找不到对应数据"),
    VERIFY_CODE_ERROR(500006,"验证码错误"),
    PARAMETER_ISNULL(500007,"参数为空"),
    PARAMETER_ILLEGAL(500008,"参数不合法"),
    UPLOAD_IMAGE_ILLEGAL(500009,"上传的图片不能为空"),
    EMPTY_PAGE_SIZE_OR_PAGE_INDEX(500010,"分页大小或页码为空"),
    ADD_DATA_ERROR(500011,"添加数据失败"),
    EDIT_DATA_ERROR(500012,"编辑数据失败"),
    OPERATE_FAILED(500013,"本次操作失败"),
    CANT_OPERATE_SYS_DATA(500014,"系统内置数据不可操作"),


    // 系统相关 格式600 xxx
    LOGIN_ERROR(600001,"用户名或密码错误，请重新登录"),
    USERNAME_EXIST(600002,"用户名重复"),
    PSW_FORMAT_ERROR(600003,"密码是由8至16位的数字和字母组成，请重新输入"),
    SEX_FORMAT_ERROR(600003,"性别未识别"),
    ROLE_NAME_EXIST(600004, "角色名重复"),
    ROLE_EDIT_NOT_ALLOWED(600005, "系统内置角色请勿删除或改名"),
    DICT_CODE_EXIST(600006, "字典编码重复"),

    // 记账相关 格式700 xxx
    INSERT_RECORD_ERROR(700001,"新增记账记录失败"),
    UPDATE_RECORD_ERROR(700002,"更新记账记录失败"),
    OPERATE_RECORD_FORBIDDEN(700003,"禁止操作他人记账记录"),
    DEL_RECORD_ERROR(700004,"删除记账记录失败"),
    RECORD_TYPE_CODE_ERROR(700005,"记账类型编码错误"),
    RECORD_ACCOUNT_SIZE_TOO_MUCH(700006,"记账账户请勿过多"),
    DEL_RECORD_BOOK_ERROR(700007,"删除账单失败"),
    ONE_DEFAULT_RECORD_BOOK(700008,"默认账单有且仅有一个"),
    OPERATE_RECORD_ACCOUNT_FORBIDDEN(700009,"禁止操作他人记账账户"),
    SOURCE_ACCOUNT_NOT_NULL(700010,"源账户不能为空"),
    SOURCE_CANT_EQUAL_TARGET_ACCOUNT(700011,"转出与转入账户不能相同"),
    OPERATE_RECORD_BOOK_FORBIDDEN(7000012,"禁止操作他人记账账单"),
    TARGET_RECORD_ACCOUNT_NOT_PAYMENT(700013, "目标账户不能为应收应付类型"),
    SOURCE_RECORD_ACCOUNT_PAYMENT_ONLY(700014, "源账户只能为应收应付类型"),
    SOURCE_RECORD_ACCOUNT_NOT_PAYMENT(700015, "源账户不能为应收应付类型"),
    TARGET_RECORD_ACCOUNT_PAYMENT_ONLY(700016, "目标账户只能为应收应付类型"),
    RECORD_CATEGORY_ERROR(700017, "记账类别错误")
    ;

    private final int retCode;
    private String message;    
    
    CodeMsg(int retCode, String message) {
        this.retCode = retCode;
        this.message = message;
    }
    public int getRetCode() {
        return retCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
