package com.patsuyash.messageservice.service.exception;

public class MessageAlreadyExistsException extends RuntimeException {

    public MessageAlreadyExistsException(final String message) {
        super(message);
    }
}
