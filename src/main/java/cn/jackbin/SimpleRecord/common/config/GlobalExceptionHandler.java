package cn.jackbin.SimpleRecord.common.config;

import cn.jackbin.SimpleRecord.dto.CodeMsg;
import cn.jackbin.SimpleRecord.dto.Result;
import cn.jackbin.SimpleRecord.exception.BusinessException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: cn.jackbin.SimpleRecord.common.config
 * @date: 2020/7/27 20:10
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     *@description: 默认异常处理
     *@params: [e]
     *@return: cn.jackbin.SimpleRecord.dto.Result
     *@createTime: 2020/7/27 20:24
     *@author: edit by bin
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error(e.getMessage(),e);
        return Result.error(CodeMsg.ERROR);
    }

    @ExceptionHandler(JwtException.class)
    public Result handleJwtException(JwtException e) {
        log.error(e.getMessage(),e);
        return Result.error(CodeMsg.JWT_EXCEPTION,e.getMessage());
    }

    /**
     *@description: 参数校验不合法异常
     *@params: [e]
     *@return: cn.jackbin.SimpleRecord.dto.Result
     *@createTime: 2020/7/27 20:23
     *@author: edit by bin
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationBodyException(MethodArgumentNotValidException e){
        log.error(e.getMessage(),e);
        for (ObjectError s : e.getBindingResult().getAllErrors()) {
            return Result.error(CodeMsg.FAILED, s.getDefaultMessage());
        }
        return Result.error(CodeMsg.FAILED,e.getMessage());
    }

    /**
     *@description: 业务异常统一处理
     *@params: [e]
     *@return: cn.jackbin.SimpleRecord.dto.Result
     *@createTime: 2020/7/27 21:18
     *@author: edit by bin
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        log.error(e.getMessage(),e);
        return Result.error(e.getCodeMsg(),e.getMessage());
    }
}
