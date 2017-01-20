package com.snail.exception;


import com.snail.domain.dto.RespDto;

public interface ExceptionHandler {

   <T extends Throwable> boolean support(Class<T> e);

    RespDto handle(Throwable e);
}