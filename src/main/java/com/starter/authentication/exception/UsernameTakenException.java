package com.starter.authentication.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsernameTakenException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(UsernameTakenException.class);

    public UsernameTakenException(String message) {
        super(message);
        logger.info(message);
    }
}
