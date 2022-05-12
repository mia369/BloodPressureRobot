package com.ht.bpr.service.impl;

import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.mapper.FamilyMemberMapper;
import com.ht.bpr.service.FamilyMemberService;
import com.ht.bpr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:26
 * @description
 */
@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {
    private static final Logger logger = LoggerFactory.getLogger(FamilyMemberServiceImpl.class);

    @Autowired
    private FamilyMemberMapper familyMemberMapper;
    @Autowired
    private UserService userService;

    @Override
    public FamilyMember selectByOpenId(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId is null");
        }
        FamilyMember members = familyMemberMapper.selectByOpenId(openId);
        return members;
    }

    @Override
    public FamilyMemberVo add(FamilyMember member) {
        //校验
        if (StringUtils.isBlank(member.getOpenId())) {
            throw new RuntimeException("openId is null");
        }
        if (StringUtils.isBlank(member.getFamilyId())) {
            throw new RuntimeException("familyId is null");
        }
        //组装member对象
        FamilyMember familyMember = new FamilyMember();
        familyMember.setOpenId(member.getOpenId());
        familyMember.setFamilyId(member.getFamilyId());
        familyMember.setCreateTime(new Date());
        //保存member表
        familyMemberMapper.add(familyMember);
        //member转换为memberVo
        User user = userService.selectOne(member.getOpenId());
        FamilyMemberVo memberVo = new FamilyMemberVo();
        memberVo.setId(familyMember.getId());
        memberVo.setOpenId(familyMember.getOpenId());
        memberVo.setFamilyId(familyMember.getFamilyId());
        memberVo.setCreateTime(familyMember.getCreateTime());
        memberVo.setNickName(user.getNickName());
        memberVo.setAge(user.getAge());
        memberVo.setLastRecordTime(user.getLastRecordTime());
        return memberVo;
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
