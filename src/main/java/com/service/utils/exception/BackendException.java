package com.service.utils.exception;


import jakarta.servlet.ServletException;

public class BackendException extends ServletException {

    //todo: Enhance exception with error codes to be reflected in response
    private final int httpStatusCode;

    public BackendException(String errorMessage, int httpStatusCode) {
        super(errorMessage);
        this.httpStatusCode = httpStatusCode;
    }

    public BackendException(String errorMessage) {
        super(errorMessage);
        this.httpStatusCode = 500;
    }

    public BackendException(Exception e) {
        super(e.getMessage());
        this.httpStatusCode = 500;
    }
}
