package com.ht.bpr.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/8 15:21
 * @description
 */
public enum UserDetailsField {
    NICKNAME(1, "nickName"),
    AGE(2, "age"),
    HEIGHT(3, "height"),
    WEIGHT(4, "weight");


    private Integer code;
    private String name;

    UserDetailsField(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserDetailsField getField(int code) {
        UserDetailsField[] values = UserDetailsField.values();
        for (UserDetailsField value : values) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

    public static UserDetailsField getField(String name) {
        UserDetailsField[] values = UserDetailsField.values();
        for (UserDetailsField value : values) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
