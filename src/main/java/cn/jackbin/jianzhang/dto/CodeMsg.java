package cn.jackbin.jianzhang.dto;

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
    // 通用异常
    public static CodeMsg ERROR = new CodeMsg(-1,"error");
    public static CodeMsg FAILED = new CodeMsg(100,"接口调用失败");
    public static CodeMsg SERVER_EXCEPTION = new CodeMsg(101,"服务端异常");



    // 通用业务 格式500 xxx
    public static CodeMsg USER_NOT_EXSIST = new CodeMsg(500002,"用户不存在");
    public static CodeMsg ONLINE_USER_OVER = new CodeMsg(500003,"在线用户数超出允许登录的最大用户限制。");
    public static CodeMsg SESSION_NOT_EXSIST =  new CodeMsg(500004,"不存在离线session数据");
    public static CodeMsg NOT_FIND_DATA = new CodeMsg(500005,"查找不到对应数据");
    public static CodeMsg VERIFYCODE_ERROR = new CodeMsg(500006,"验证码错误");
    public static CodeMsg PARAMETER_ISNULL = new CodeMsg(500007,"参数为空");
    public static CodeMsg PARAMETER_ILLEGAL = new CodeMsg(500008,"参数不合法");
    public static CodeMsg UPLOAD_IMAGE_ILLEGAL = new CodeMsg(500009,"上传的图片不能为空");
    public static CodeMsg EMPTY_PAGESIZE_OR_PAGEINDEX = new CodeMsg(500010,"分页大小或页码为空");

    //店铺业务 格式 100 xxx
    public static CodeMsg SHOP_STATUS_CHECK = new CodeMsg(100000,"审核中");
    public static CodeMsg SHOP_STATUS_OFFLINE= new CodeMsg(100001,"非法店铺");
    public static CodeMsg SHOP_OPERATION_SUCCESS= new CodeMsg(100003,"店铺相关操作成功");
    public static CodeMsg SHOP_STATUS_PASS= new CodeMsg(100004,"通过认证");
    public static CodeMsg NULL_SHOP_ID = new CodeMsg(100005,"ShopId为空");
    public static CodeMsg NULL_SHOP = new CodeMsg(100006,"店铺为空");
    public static CodeMsg NULL_SHOPLIST = new CodeMsg(100007,"店铺列表为空");

    //商品类别业务 格式：101 xxx
    public static CodeMsg PRODUCT_CATEGORY_CREATE_SUCCESS = new CodeMsg(101000,"创建成功");
    public static CodeMsg PRODUCT_CATEGORY_EMPTY_LIST = new CodeMsg(101001,"添加内容为空");
    public static CodeMsg PRODUCT_INPUT_ATLEAST_ONE = new CodeMsg(101002,"至少输入一个商品类别");
    public static CodeMsg PRODUCT_CATEGORY_DEL_SUCCESS = new CodeMsg(101003,"商品类别删除成功");

    //商品业务，格式：102 xxx
    public static CodeMsg PRODUCT_STATUS_ONLINE = new CodeMsg(1,"商品上架");
    public static CodeMsg PRODUCT_STATUS_OFFLINE = new CodeMsg(0,"商品下架");
    public static CodeMsg PRODUCT_STATUS_NONE = new CodeMsg(-1,"商品不显示");
    public static CodeMsg PRODUCT_ADD_SUCCESS = new CodeMsg(102000,"商品创建成功");
    public static CodeMsg PRODUCT_ADD_EMPTY = new CodeMsg(102001,"空值错误信息");
    public static CodeMsg PRODUCT_IS_NULL = new CodeMsg(102002,"商品信息为空");
    public static CodeMsg PRODUCT_UPDATE_SUCCESS = new CodeMsg(102003,"商品更新成功");
    public static CodeMsg PRODUCT_UPDATE_FAILED = new CodeMsg(102004,"商品不显示");
    public static CodeMsg PRODUCTID_IS_NULL = new CodeMsg(102005,"商品ID为空");

    //登录业务，格式：103 xxx
    public static CodeMsg ADMIN_LOGIN_SUCCESS = new CodeMsg(103001,"管理员登录成功");
    public static CodeMsg USER_LOGIN_SUCCESS = new CodeMsg(103002,"用户登录成功");
    public static CodeMsg USER_LOGIN_FAILED = new CodeMsg(103003,"用户登录失败");
    public static CodeMsg USER_LOGIN_WITHOUT_PERMISSION = new CodeMsg(103004,"该用户没有权限");

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
