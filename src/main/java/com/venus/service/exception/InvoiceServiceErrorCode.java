package com.venus.service.exception;

/**
 * Error code messages for the Invoice Service
 */
public class InvoiceServiceErrorCode {

    private final String errorCode;

    private final String message;

    public InvoiceServiceErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}