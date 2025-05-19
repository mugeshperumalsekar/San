package com.ponsun.san.infrastructure.exceptions;

public class ApplicationException extends AbstractPlatformException{

    public ApplicationException(String message){
        super("error.msg.generic",message);
    }


}
