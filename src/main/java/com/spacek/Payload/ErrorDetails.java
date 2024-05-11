package com.spacek.Payload;

import lombok.Getter;

import java.util.Date;
@Getter
public class ErrorDetails {
    private final Date timeStamp;
    private final String message;
    private final String details;
    public ErrorDetails(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

}
