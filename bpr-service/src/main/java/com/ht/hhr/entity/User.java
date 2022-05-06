package com.ht.hhr.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 19:48
 * @description
 */
@Data
public class User {
    private Integer id;
    private String openId;
    private String username;
    private String password;
    private String salt;
    private Integer deleteMark;
    private Date createTime;
    private Date updateTime;
}
