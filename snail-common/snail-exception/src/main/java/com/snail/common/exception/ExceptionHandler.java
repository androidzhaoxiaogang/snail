package com.snail.common.exception;


import com.snail.common.domain.dto.RespDto;

public interface ExceptionHandler {

   <T extends Throwable> boolean support(Class<T> e);

    RespDto handle(Throwable e);
}