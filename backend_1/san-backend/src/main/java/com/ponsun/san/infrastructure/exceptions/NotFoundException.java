package com.ponsun.san.infrastructure.exceptions;

public class NotFoundException extends AbstractPlatformException {

    public NotFoundException(String message) {
        super("error.msg.not.found", message);
    }
}
