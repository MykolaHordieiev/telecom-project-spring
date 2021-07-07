package org.project.spring.telecom.product;


import org.project.spring.telecom.infra.exception.TelecomException;

public class ProductException extends TelecomException {

    ProductException(String message) {
        super(message);
    }
}
