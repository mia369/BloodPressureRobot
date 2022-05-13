package com.ht.bpr.service.impl;

import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.mapper.FamilyMemberMapper;
import com.ht.bpr.service.FamilyMemberService;
import com.ht.bpr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public void deleteBatchByFamilyId(String familyId) {
        if (StringUtils.isBlank(familyId)) {
            throw new RuntimeException("familyId is null");
        }
        familyMemberMapper.deleteByFamilyId(familyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMember(FamilyMemberVo memberVo) {
        if (memberVo == null) {
            throw new RuntimeException("familyMemberVo is null");
        }
        if (StringUtils.isBlank(memberVo.getFamilyId())) {
            throw new RuntimeException("familyId is null");
        }
        if (StringUtils.isBlank(memberVo.getOpenId())) {
            throw new RuntimeException("openId of member is null");
        }
        FamilyMember member = familyMemberMapper.selectByFamilyIdAndOpenId(memberVo.getFamilyId(), memberVo.getOpenId());
        if (member != null) {
            familyMemberMapper.deleteByFamilyIdAndOpenId(memberVo.getFamilyId(), memberVo.getOpenId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFamilyIdentity(FamilyMemberVo familyMemberVo) {
        //校验
        if (familyMemberVo == null) {
            throw new RuntimeException("familyMemberVo is null");
        }
        if (StringUtils.isBlank(familyMemberVo.getFamilyId())) {
            throw new RuntimeException("familyId is null");
        }
        if (StringUtils.isBlank(familyMemberVo.getOpenId())) {
            throw new RuntimeException("openId of member is null");
        }
        if (StringUtils.isBlank(familyMemberVo.getFamilyIdentity())) {
            throw new RuntimeException("familyIdentity is null");
        }
        //校验member是否存在, 如存在, 则修改
        String familyId = familyMemberVo.getFamilyId();
        String openId = familyMemberVo.getOpenId();
        String newIdentity = familyMemberVo.getFamilyIdentity();
        FamilyMember checkMember = familyMemberMapper.selectByFamilyIdAndOpenId(familyId, openId);
        if (checkMember == null) {
            throw new RuntimeException("familyMember does not exist");
        }
        if (StringUtils.isNotBlank(checkMember.getFamilyIdentity()) && checkMember.getFamilyIdentity().equals(newIdentity)) {
            return;
        }
        familyMemberMapper.updateFamilyIdentity(familyId, openId, newIdentity);
    }
}
