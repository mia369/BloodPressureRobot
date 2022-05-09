package com.ht.bpr.entity;

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
    private String nickName;
    private String avatarUrl;
    private Integer age;
    private Integer height;
    private Integer weight;
    private String openId;
    private String unionId;
    private String lastRecordTime;
    private Date createTime;
    private Date updateTime;
}
