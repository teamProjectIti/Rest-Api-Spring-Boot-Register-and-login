package com.spacek.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException {
    private HttpStatus Status;
    private String Messages;

    public BlogApiException(String message, HttpStatus status, String message1) {
        super(message);
        Messages = message1;
        Status = status;
    }

    public BlogApiException(HttpStatus httpStatus, String message) {
        Messages = message;
        Status = httpStatus;
    }

    public HttpStatus getStatus() {
        return Status;
    }

    public String getMessages() {
        return Messages;
    }
}
