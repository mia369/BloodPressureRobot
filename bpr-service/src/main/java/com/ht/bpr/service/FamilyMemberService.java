package com.ht.bpr.service;

import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:25
 * @description
 */
public interface FamilyMemberService {

    FamilyMember selectByOpenId(String openId);

    FamilyMemberVo add(FamilyMember member);

    List<FamilyMember> selectByFamilyId(String familyId);

    void deleteBatchByFamilyId(String familyId);

    void deleteMember(FamilyMemberVo memberVo);

    void updateFamilyIdentity(FamilyMemberVo familyMemberVo);
}
