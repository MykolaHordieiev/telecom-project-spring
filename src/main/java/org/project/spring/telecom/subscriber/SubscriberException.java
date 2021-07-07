package org.project.spring.telecom.subscriber;


import org.project.spring.telecom.infra.exception.TelecomException;

public class SubscriberException extends TelecomException {

    SubscriberException(String message) {
        super(message);
    }
}
