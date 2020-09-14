package cn.jackbin.SimpleRecord.dto;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 只有在需要标识特殊业务时，才能调用此消息类
 * @date:2019/8/2
 **/
@SuppressWarnings("ALL")
public class CodeMsg {
    private int retCode;
    private String message;
    // 按照模块定义CodeMsg
    // 成功
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    // 通用异常
    public static CodeMsg ERROR = new CodeMsg(-1,"接口调用异常");
    public static CodeMsg FAILED = new CodeMsg(100,"接口调用失败");
    public static CodeMsg SERVER_EXCEPTION = new CodeMsg(101,"服务端异常");
    public static CodeMsg JWT_EXCEPTION = new CodeMsg(102,"JWT校验异常");
    public static CodeMsg WITHOUT_PERMISSION = new CodeMsg(103, "权限不足，请联系管理员");
    public static CodeMsg TOKEN_EXPIRED = new CodeMsg(104, "token失效");



    // 通用业务 格式500 xxx
    public static CodeMsg BUSINESS_ERROR = new CodeMsg(500000,"业务异常");
    public static CodeMsg ONLINE_USER_OVER = new CodeMsg(500003,"在线用户数超出允许登录的最大用户限制。");
    public static CodeMsg SESSION_NOT_EXSIST =  new CodeMsg(500004,"不存在离线session数据");
    public static CodeMsg NOT_FIND_DATA = new CodeMsg(500005,"查找不到对应数据");
    public static CodeMsg VERIFYCODE_ERROR = new CodeMsg(500006,"验证码错误");
    public static CodeMsg PARAMETER_ISNULL = new CodeMsg(500007,"参数为空");
    public static CodeMsg PARAMETER_ILLEGAL = new CodeMsg(500008,"参数不合法");
    public static CodeMsg UPLOAD_IMAGE_ILLEGAL = new CodeMsg(500009,"上传的图片不能为空");
    public static CodeMsg EMPTY_PAGESIZE_OR_PAGEINDEX = new CodeMsg(500010,"分页大小或页码为空");


    // 用户相关 格式600 xxx
    public static CodeMsg LOGIN_ERROR = new CodeMsg(600001,"登录失败");
    public static CodeMsg USERNAME_EXIST = new CodeMsg(600002,"用户名重复");
    public static CodeMsg PSW_FORMAT_ERROR = new CodeMsg(600003,"密码是由8至16位的数字和字母组成，请重新输入");
    public static CodeMsg SEX_FORMAT_ERROR = new CodeMsg(600003,"性别未识别");

    private CodeMsg(int retCode, String message) {
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
