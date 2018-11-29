package com.starter.authentication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailTakenException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(EmailTakenException.class);

    public EmailTakenException(String message) {
        super(message);
        logger.info(message);
    }
}
