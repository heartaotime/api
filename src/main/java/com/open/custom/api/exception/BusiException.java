package com.open.custom.api.exception;

public class BusiException extends RuntimeException {

    private String code;

    public BusiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusiException(String message) {
        super(message);
        this.code = "-1";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
