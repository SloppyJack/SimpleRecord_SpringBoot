package cn.jackbin.jianzhang.exception;

import cn.jackbin.jianzhang.dto.CodeMsg;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.jianzhang.exception
 * @date: 2020/7/27 21:08
 **/
public class NotFoundException  extends BusinessException{
    public NotFoundException(String message) {
        super(message);
        super.codeMsg = CodeMsg.NOT_FOUND;
    }

    public NotFoundException(CodeMsg codeMsg,String message) {
        super(message);
        super.codeMsg = codeMsg;
    }
}
