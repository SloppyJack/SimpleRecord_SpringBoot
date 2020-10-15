package cn.jackbin.SimpleRecord.exception;

import cn.jackbin.SimpleRecord.constant.CodeMsg;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.exception
 * @date: 2020/7/27 21:08
 **/
public class NotFoundException  extends BaseException {
    public NotFoundException(String message) {
        super(message);
        super.codeMsg = CodeMsg.NOT_FIND_DATA;
    }

    public NotFoundException(CodeMsg codeMsg,String message) {
        super(message);
        super.codeMsg = codeMsg;
    }
}
