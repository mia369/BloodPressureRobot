package com.ht.bpr.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/7 10:43
 * @description
 */
@Data
public class FamilyMember {
    private Integer id;
    private String openId;
    private String familyId;
    private String familyIdentity;
    private Date createTime;
    private Date updateTime;
}
