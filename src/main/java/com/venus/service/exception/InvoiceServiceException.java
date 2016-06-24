package com.venus.service.exception;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hrishikeshjoshi on 6/18/16.
 */
public class InvoiceServiceException extends Exception {

    private final Set<InvoiceServiceErrorCode> errorCodes = new HashSet<InvoiceServiceErrorCode>();

    public void addErrorCode(InvoiceServiceErrorCode errorCode) {
        errorCodes.add(errorCode);
    }

    public void addNewErrorCode(String code, String message){
        this.errorCodes.add(new InvoiceServiceErrorCode(code,message));
    }

    public InvoiceServiceException() {
    }

    public InvoiceServiceException(String message) {
        super(message);
    }

    public InvoiceServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoiceServiceException(Throwable cause) {
        super(cause);
    }

    public InvoiceServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Set<InvoiceServiceErrorCode> getErrorCodes() {
        return errorCodes;
    }


}
