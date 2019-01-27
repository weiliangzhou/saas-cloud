package com.wegoo.exception;

/**
 * 封装的业务异常类
 * Created by 二师兄超级帅 on 2018/7/8.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message){
        super(message);
    }
    public BusinessException(String message,Throwable cause){
        super(message,cause);
    }


}