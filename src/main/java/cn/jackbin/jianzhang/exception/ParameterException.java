package cn.jackbin.jianzhang.exception;

import cn.jackbin.jianzhang.dto.CodeMsg;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.exception
 * @date: 2020/7/28 19:41
 **/
public class ParameterException extends BusinessException {
    public ParameterException(String message) {
        super(message);
        super.codeMsg = CodeMsg.PARAMETER_ILLEGAL;
    }
}
