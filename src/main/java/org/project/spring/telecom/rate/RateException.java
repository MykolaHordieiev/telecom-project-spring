package org.project.spring.telecom.rate;

import org.project.spring.telecom.infra.exception.TelecomException;

public class RateException extends TelecomException {

    RateException(String message) {
        super(message);
    }
}
