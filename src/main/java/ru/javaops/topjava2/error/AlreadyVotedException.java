package ru.javaops.topjava2.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class AlreadyVotedException extends AppException{
    public AlreadyVotedException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, "User with id= " + message + " has already voted today", ErrorAttributeOptions.of(MESSAGE));
    }
}
