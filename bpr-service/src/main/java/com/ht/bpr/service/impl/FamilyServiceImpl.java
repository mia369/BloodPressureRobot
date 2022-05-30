package com.ht.bpr.service.impl;

import com.ht.bpr.common.BprResultStatus;
import com.ht.bpr.common.Constants;
import com.ht.bpr.entity.Family;
import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.exception.BprException;
import com.ht.bpr.mapper.FamilyMapper;
import com.ht.bpr.mapper.FamilyMemberMapper;
import com.ht.bpr.service.FamilyService;
import com.ht.bpr.service.UserService;
import com.ht.bpr.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:26
 * @description
 */
@Service
public class FamilyServiceImpl implements FamilyService {

    private static final Logger logger = LoggerFactory.getLogger(FamilyServiceImpl.class);

    @Autowired
    private FamilyMapper familyMapper;
    @Autowired
    private FamilyMemberMapper memberMapper;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFamily(String familyName, String openId) {
        //校验familyName, openId
        if (StringUtils.isBlank(familyName)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyName is null");
        }
        if (StringUtils.isBlank(openId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        //校验用户是否合法
        User checkUser = userService.selectOne(openId);
        if (checkUser == null) {
            throw new BprException(BprResultStatus.USER_NOT_EXIST, "user does not exist");
        }
        //生成familyId
        Date createTime = new Date();
        String rawFamilyId = String.format(Constants.FAMILY_ID_FORMAT, openId, createTime);
        String familyId = Md5Util.md5(rawFamilyId);
        //保存到family表
        Family family = new Family();
        family.setFamilyId(familyId);
        family.setFamilyName(familyName);
        family.setFamilyManager(openId);
        family.setCreateTime(createTime);
        familyMapper.add(family);
        //manager保存到member表
        FamilyMember member = new FamilyMember();
        member.setOpenId(openId);
        member.setFamilyId(familyId);
        member.setCreateTime(createTime);
        memberMapper.add(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FamilyVo selectByOpenId(String openId) {
        //校验
        if (StringUtils.isBlank(openId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        //查询member表
        FamilyMember familyMember = memberMapper.selectByOpenId(openId);
        //查询结果为空则表明未创建/加入家庭, 直接返回空
        if (familyMember == null) {
            return null;
        }
        //查询family表
        String familyId = familyMember.getFamilyId();
        Family family = familyMapper.selectByFamilyId(familyId);
        List<FamilyMember> members = memberMapper.selectByFamilyId(familyId);
        //查询user表, 根据openId查询user的nickName和age
        List<String> openIds = members.stream().map(m -> m.getOpenId()).collect(Collectors.toList());
        Map<String, User> userMap = userService.selectUserMapByOpenIds(openIds);
        //组装memberVo
        FamilyVo familyVo = new FamilyVo();
        List<FamilyMemberVo> memberVos = new ArrayList<>();
        for (FamilyMember member : members) {
            FamilyMemberVo memberVo = new FamilyMemberVo();
            memberVo.setId(member.getId());
            memberVo.setOpenId(member.getOpenId());
            memberVo.setFamilyId(familyId);
            memberVo.setFamilyIdentity(member.getFamilyIdentity());
            memberVo.setCreateTime(member.getCreateTime());
            memberVo.setUpdateTime(member.getUpdateTime());
            User user = userMap.get(member.getOpenId());
            if (user != null) {
                memberVo.setNickName(user.getNickName());
                memberVo.setAge(user.getAge());
                memberVo.setLastRecordTime(user.getLastRecordTime());
            }
            memberVos.add(memberVo);
            if (member.getOpenId().equals(family.getFamilyManager())) {
                familyVo.setFamilyManager(memberVo);
            }
        }
        //组装familyVo
        familyVo.setId(family.getId());
        familyVo.setFamilyId(familyId);
        familyVo.setFamilyName(family.getFamilyName());
        familyVo.setFamilyMemberVos(memberVos);
        familyVo.setCreateTime(family.getCreateTime());
        familyVo.setUpdateTime(family.getUpdateTime());
        return familyVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMember(FamilyMemberVo memberVo) {
        if (memberVo == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "memberVo is null");
        }
        String openId = memberVo.getOpenId();
        if (openId == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        String familyId = memberVo.getFamilyId();
        if (familyId == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyId is null");
        }
        FamilyMember member = new FamilyMember();
        member.setOpenId(openId);
        member.setFamilyId(familyId);
        memberMapper.add(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFamily(FamilyVo familyVo) {
        if (familyVo == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyVo is null");
        }
        String familyId = familyVo.getFamilyId();
        if (StringUtils.isBlank(familyId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyId is null");
        }
        Family family = familyMapper.selectByFamilyId(familyId);
        if (family == null) {
            throw new BprException(BprResultStatus.FAMILY_NOT_EXIST);
        }
        familyMapper.deleteByFamilyId(familyId);
        memberMapper.deleteByFamilyId(familyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMember(FamilyMemberVo memberVo) {
        if (memberVo == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "memberVo is null");
        }
        String familyId = memberVo.getFamilyId();
        if (StringUtils.isBlank(familyId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyId is null");
        }
        String openId = memberVo.getOpenId();
        if (StringUtils.isBlank(openId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId of member is null");
        }
        //删除成员
        FamilyMember member = memberMapper.selectByFamilyIdAndOpenId(familyId, openId);
        if (member != null) {
            memberMapper.deleteByFamilyIdAndOpenId(familyId, openId);
        }
    }

    @Override
    public void exitFamily(FamilyMemberVo memberVo) {
        //校验
        if (memberVo == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "memberVo is null");
        }
        String familyId = memberVo.getFamilyId();
        if (StringUtils.isBlank(familyId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyId is null");
        }
        String openId = memberVo.getOpenId();
        if (StringUtils.isBlank(openId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId of member is null");
        }
        //查询member表, 如果有记录则删除
        FamilyMember member = memberMapper.selectByFamilyIdAndOpenId(familyId, openId);
        if (member != null) {
            memberMapper.deleteByFamilyIdAndOpenId(familyId, openId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFamilyIdentity(FamilyMemberVo familyMemberVo) {
        //校验
        if (familyMemberVo == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyMemberVo is null");
        }
        if (StringUtils.isBlank(familyMemberVo.getFamilyId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyId is null");
        }
        if (StringUtils.isBlank(familyMemberVo.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId of member is null");
        }
        if(StringUtils.isBlank(familyMemberVo.getFamilyIdentity())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "familyIdentity is null");
        }
        //校验member是否存在, 如存在, 则修改
        String familyId = familyMemberVo.getFamilyId();
        String openId = familyMemberVo.getOpenId();
        String newIdentity = familyMemberVo.getFamilyIdentity();
        FamilyMember checkMember = memberMapper.selectByFamilyIdAndOpenId(familyId, openId);
        if (checkMember == null) {
            throw new BprException(BprResultStatus.FAMILY_MEMBER_NOT_EXIST, "member does not exist");
        }
        if (StringUtils.isNotBlank(checkMember.getFamilyIdentity()) && checkMember.getFamilyIdentity().equals(newIdentity)) {
            return;
        }
        memberMapper.updateFamilyIdentity(familyId, openId, newIdentity);
    }


    private List<FamilyMemberVo> getFamilyMemberVos(FamilyMemberVo familyMemberVo) {
        //重新查询家庭成员
        List<FamilyMember> members = memberMapper.selectByFamilyId(familyMemberVo.getFamilyId());
        //members 转换为 memberVos
        List<String> openIds = members.stream().map(m -> m.getOpenId()).collect(Collectors.toList());
        Map<String, User> userMap = userService.selectUserMapByOpenIds(openIds);
        //组装memberVo
        List<FamilyMemberVo> memberVos = new ArrayList<>();
        for (FamilyMember member : members) {
            FamilyMemberVo memberVo = new FamilyMemberVo();
            memberVo.setId(member.getId());
            memberVo.setOpenId(member.getOpenId());
            memberVo.setFamilyId(familyMemberVo.getFamilyId());
            memberVo.setFamilyIdentity(member.getFamilyIdentity());
            memberVo.setCreateTime(member.getCreateTime());
            memberVo.setUpdateTime(member.getUpdateTime());
            User user = userMap.get(member.getOpenId());
            memberVo.setNickName(user.getNickName());
            memberVo.setAge(user.getAge());
            memberVo.setLastRecordTime(user.getLastRecordTime());
            memberVos.add(memberVo);
        }
        return memberVos;
    }

}
