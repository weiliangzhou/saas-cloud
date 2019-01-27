package com.wegoo.exception;

import com.wegoo.baseresult.BaseResult;
import com.wegoo.constants.BaseResultConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 二师兄超级帅 on 2018/7/8.
 * * @ControllerAdvice + @ExceptionHandler 实现全局的 Controller 层的异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    BaseResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new BaseResult(BaseResultConstants.HTTP_CODE_500, "系统繁忙，请稍后重试!", null);
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    BaseResult handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return new BaseResult(BaseResultConstants.HTTP_CODE_500, e.getMessage(), null);
    }


    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return new BaseResult(BaseResultConstants.HTTP_CODE_500, e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), null);
    }
}