package com.lg.springbootexample.tools;

/**
 * @author lg
 * @date 2018-09-21
 */
public enum ErrorCode {
    /**
     * 错误信息
     */
    SUCCESS(10000, "Success"),

    FAIL(10001, "Failure");


    private int code;
    private String msg;

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
