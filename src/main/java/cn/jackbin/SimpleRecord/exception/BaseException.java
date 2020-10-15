package cn.jackbin.SimpleRecord.exception;

import cn.jackbin.SimpleRecord.constant.CodeMsg;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.exception
 * @date: 2020/7/27 21:16
 **/
public class BaseException extends RuntimeException{
    protected CodeMsg codeMsg;

    public BaseException(String message) {
        super(message);
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
