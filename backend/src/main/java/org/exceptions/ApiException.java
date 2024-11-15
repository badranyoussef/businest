package org.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private int statusCode;
    private String timeStamp;

    public ApiException(int statusCode, String message, String timeStamp) {
        super(message);
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
    }
}
