package com.ht.hhr.entity;

import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/6 23:45
 * @description
 */
@Data
public class LoginResponse {
    private String openId;
    private String sessionKey;
}
