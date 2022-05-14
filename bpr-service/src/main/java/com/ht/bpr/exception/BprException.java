package com.ht.bpr.exception;

import com.ht.bpr.common.BprResultStatus;
import com.ht.bpr.common.ResultStatus;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/14 14:37
 * @description
 */
public class BprException extends RuntimeException {
    private int code;
    private String message;

    public BprException() {
    }

    public BprException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BprException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public BprException(ResultStatus resultStatus) {
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public BprException(ResultStatus resultStatus, String message) {
        this.code = resultStatus.getCode();
        this.message = message;
    }

    public BprException(Throwable e) {
        super(e);
    }

    public BprException(String message, Throwable e) {
        super(message, e);
        this.code = BprResultStatus.FAILED.getCode();
        this.message = message;
    }

    public BprException(ResultStatus resultStatus, Throwable e) {
        super(resultStatus.getMessage(), e);
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
