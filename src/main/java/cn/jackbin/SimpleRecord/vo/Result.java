package cn.jackbin.SimpleRecord.vo;

import cn.jackbin.SimpleRecord.constant.CodeMsg;

import java.io.Serializable;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: com.ssm.dto
 * @date:2019/8/5
 **/
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4274097396250944384L;

    private String message;
    private int retCode;
    private T data;
    private Result(T data) {
        this.retCode = CodeMsg.SUCCESS.getRetCode();
        this.message = CodeMsg.SUCCESS.getMessage();
        this.data = data;
    }
    private Result(CodeMsg cm) {
        if(cm == null){
            return;
        }
        this.retCode = cm.getRetCode();
        this.message = cm.getMessage();
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success(){
        return (Result<T>) success("");
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm){
        return new Result<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm,String msg){
        cm.setMessage(msg);
        return new Result<T>(cm);
    }
    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
    public int getRetCode() {
        return retCode;
    }
}
