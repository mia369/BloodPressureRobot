package com.ht.bpr.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/7 10:33
 * @description
 */
@Data
public class Family {
    private Integer id;
    private String familyId;
    private String familyName;
    private String familyManager;
    private Date createTime;
    private Date updateTime;
}
