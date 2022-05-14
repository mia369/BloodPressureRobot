package com.ht.bpr.common;

import com.ht.bpr.exception.BprException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:47
 * @description
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1076684197300510806L;

    private boolean success;
    private int code;
    private String message;
    private T result;

    private static final Result EMPTY_SUCCESS_RESULT = Result.success(null);

    public static <T> Result<T> success() {
        return EMPTY_SUCCESS_RESULT;
    }

    public static <T> Result<T> success(T obj) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(BprResultStatus.OK.getCode());
        result.setMessage(BprResultStatus.OK.getMessage());
        result.setResult(obj);
        return result;
    }

    public static Result failure(ResultStatus status) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        return result;
    }

    public static Result failure(ResultStatus status, String message) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setCode(status.getCode());
        result.setMessage(message);
        return result;
    }

    public static Result failure(int code, String message) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static Result failure(String message) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

}
