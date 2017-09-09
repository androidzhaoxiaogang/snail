package com.snail.common.exception;

/**
 */
public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(message, 404);
    }
}
