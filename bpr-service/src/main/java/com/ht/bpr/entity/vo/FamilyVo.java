package com.ht.bpr.entity.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 20:14
 * @description
 */
@Data
public class FamilyVo {
    private Integer id;
    private String familyId;
    private String familyName;
    private FamilyMemberVo familyManager;
    private List<FamilyMemberVo> familyMemberVos;
    private Date createTime;
    private Date updateTime;
}
