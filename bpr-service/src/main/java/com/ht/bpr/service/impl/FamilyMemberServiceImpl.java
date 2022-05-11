package com.ht.bpr.service.impl;

import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.mapper.FamilyMemberMapper;
import com.ht.bpr.service.FamilyMemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:26
 * @description
 */
@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {
    @Autowired
    private FamilyMemberMapper familyMemberMapper;

    @Override
    public FamilyMember selectByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId is null");
        }
        FamilyMember familyMember = familyMemberMapper.selectByOpenId(openId);
        return familyMember;
    }

    @Override
    public void add(FamilyMember member) {
        //校验
        if (StringUtils.isBlank(member.getOpenId())) {
            throw new RuntimeException("openId is null");
        }
        if (StringUtils.isBlank(member.getFamilyId())) {
            throw new RuntimeException("familyId is null");
        }
        Integer managerMark = member.getFamilyManagerMark();
        if (managerMark == null) {
            managerMark = 0;
        }
        Date createTime = member.getCreateTime();
        if (createTime == null) {
            createTime = new Date();
        }
        //组装member对象
        FamilyMember familyMember = new FamilyMember();
        familyMember.setOpenId(member.getOpenId());
        familyMember.setFamilyId(member.getFamilyId());
        familyMember.setFamilyManagerMark(managerMark);
        familyMember.setCreateTime(createTime);

        //保存member表
        familyMemberMapper.add(familyMember);
    }

    @Override
    public List<FamilyMember> selectByFamilyId(String familyId) {
        if (StringUtils.isBlank(familyId)) {
            throw new RuntimeException("familyId is null");
        }
        List<FamilyMember> members = familyMemberMapper.selectByFamilyId(familyId);
        return members;
    }
}
