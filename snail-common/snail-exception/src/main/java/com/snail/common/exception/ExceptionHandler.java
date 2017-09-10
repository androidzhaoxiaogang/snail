package com.snail.common.exception;


public interface ExceptionHandler {

   <T extends Throwable> boolean support(Class<T> e);
}