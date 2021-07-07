package org.project.spring.telecom.subscribing;


import org.project.spring.telecom.infra.exception.TelecomException;

public class SubscribingException extends TelecomException {

    SubscribingException(String message) {
        super(message);
    }
}
