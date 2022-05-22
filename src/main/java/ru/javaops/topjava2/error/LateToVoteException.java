package ru.javaops.topjava2.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class LateToVoteException extends AppException{
    public LateToVoteException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, "You can't vote after " + message, ErrorAttributeOptions.of(MESSAGE));
    }
}