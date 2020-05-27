package com.lg.springbootexample.tools;

/**
 * @author ligang
 * @date 2020-05-27
 */
public class ServiceException  extends Exception{
    private ErrorCode error;

    public ServiceException(ErrorCode error) {
        super(error.getMsg());
        this.error = error;
    }

    public ErrorCode getError() {
        return error;
    }

    public void setError(ErrorCode error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return this.error.getMsg();
    }
}