package com.ht.bpr.service;

import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;


/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:25
 * @description
 */
public interface FamilyService {

    FamilyVo selectByOpenId(String openId);

    void addFamily(String familyName, String openId);

    void addMember(FamilyMemberVo familyMemberVo);

    void deleteFamily(FamilyVo familyVo);

    void deleteMember(FamilyMemberVo memberVo);

    void exitFamily(FamilyMemberVo memberVo);

    void updateFamilyIdentity(FamilyMemberVo memberVo);
}
