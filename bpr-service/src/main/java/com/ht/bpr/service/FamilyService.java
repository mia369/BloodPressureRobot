package com.ht.bpr.service;

import com.ht.bpr.entity.vo.FamilyVo;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:25
 * @description
 */
public interface FamilyService {
    FamilyVo add(FamilyVo familyVo);

    FamilyVo selectByOpenId(String openId);
}
