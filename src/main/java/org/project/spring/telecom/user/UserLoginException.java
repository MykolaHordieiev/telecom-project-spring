package org.project.spring.telecom.user;

import org.project.spring.telecom.infra.exception.TelecomException;

public class UserLoginException extends TelecomException {

    UserLoginException(String message){
        super(message);
    }
}
