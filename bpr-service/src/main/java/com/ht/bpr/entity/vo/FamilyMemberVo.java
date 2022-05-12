package com.ht.bpr.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 21:29
 * @description
 */
@Data
public class FamilyMemberVo {
    private Integer id;
    private String openId;
    private String nickName;
    private Integer age;
    private String familyId;
    private String familyIdentity;
    private String lastRecordTime;
    private Date createTime;
    private Date updateTime;
}
