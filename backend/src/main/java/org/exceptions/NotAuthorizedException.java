package org.exceptions;

import lombok.Getter;

@Getter
public class NotAuthorizedException extends RuntimeException {
    private int statusCode;
    private String timeStamp;

    public NotAuthorizedException(int statusCode, String message, String timeStamp) {
        super(message);
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
    }
}
