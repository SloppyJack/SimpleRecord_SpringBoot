package cn.jackbin.SimpleRecord.exception;

import cn.jackbin.SimpleRecord.constant.CodeMsg;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.exception
 * @date: 2020/7/28 19:41
 **/
public class ParameterException extends BaseException {
    public ParameterException(String message) {
        super(message);
        super.codeMsg = CodeMsg.PARAMETER_ILLEGAL;
    }

    public ParameterException(CodeMsg codeMsg, String message) {
        super(message);
        super.codeMsg = codeMsg;
    }

    public ParameterException(CodeMsg codeMsg) {
        super(codeMsg.getMessage());
        super.codeMsg = codeMsg;
    }
}
