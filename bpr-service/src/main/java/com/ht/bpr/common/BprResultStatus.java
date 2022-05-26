package com.ht.bpr.common;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 13:52
 * @description
 */
public enum BprResultStatus implements ResultStatus {

    OK(0000, "success"),
    FAILED(9999, "server failed"),
    PARAM_IS_NULL(1000, "param is null"),
    ILLEGAL_MD5_ALGORITHM(1001, "illegal md5 algorithm"),
    FAMILY_MEMBER_NOT_EXIST(1002, "familyMember does not exist"),
    FAMILY_NOT_EXIST(1003, "family does not exist"),
    USER_NOT_EXIST(1004, "user does not exist");

    private final Integer code;

    private final String message;

    BprResultStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultStatus{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
