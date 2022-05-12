package com.ht.bpr.service.impl;

import com.ht.bpr.common.Constants;
import com.ht.bpr.entity.Family;
import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.mapper.FamilyMapper;
import com.ht.bpr.mapper.FamilyMemberMapper;
import com.ht.bpr.service.FamilyMemberService;
import com.ht.bpr.service.FamilyService;
import com.ht.bpr.service.UserService;
import com.ht.bpr.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.provider.MD5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:26
 * @description
 */
@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyMapper familyMapper;
    @Autowired
    private FamilyMemberService familyMemberService;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FamilyVo add(FamilyVo familyVo) {
        //familyName判空
        if (StringUtils.isBlank(familyVo.getFamilyName())) {
            throw new RuntimeException("familyName is null");
        }

        //查询familyManager是否已经有家庭, 如果没有, 则可以创建
        String managerOpenId = familyVo.getFamilyManager();
        FamilyMember members = familyMemberService.selectByOpenId(managerOpenId);
        if (members != null) {
            throw new RuntimeException("User already has a family");
        }

        //生成familyId
        Date createTime = new Date();
        String rawFamilyId = String.format(Constants.FAMILY_ID_FORMAT, managerOpenId, createTime);
        String familyId = Md5Util.md5(rawFamilyId);

        //保存family表和member表
        Family family = new Family();
        family.setFamilyId(familyId);
        family.setFamilyName(familyVo.getFamilyName());
        family.setFamilyManager(managerOpenId);
        family.setCreateTime(createTime);
        familyMapper.add(family);

        FamilyMember member = new FamilyMember();
        member.setOpenId(managerOpenId);
        member.setFamilyId(familyId);
        member.setFamilyManagerMark(1);
        member.setCreateTime(createTime);
        familyMemberService.add(member);

        //member转换为memberVo
        User user = userService.selectOne(managerOpenId);
        List<FamilyMemberVo> memberVos = new ArrayList<>();
        FamilyMemberVo memberVo = new FamilyMemberVo();
        memberVo.setId(member.getId());
        memberVo.setOpenId(managerOpenId);
        memberVo.setFamilyId(familyId);
        memberVo.setFamilyManagerMark(member.getFamilyManagerMark());
        memberVo.setNickName(user.getNickName());
        memberVo.setAge(user.getAge());
        memberVo.setCreateTime(createTime);
        memberVos.add(memberVo);

        //组装familyVo
        FamilyVo vo = new FamilyVo();
        vo.setId(family.getId());
        vo.setFamilyId(familyId);
        vo.setFamilyName(familyVo.getFamilyName());
        vo.setFamilyManager(managerOpenId);
        vo.setCreateTime(createTime);
        vo.setFamilyMemberVos(memberVos);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FamilyVo selectByOpenId(String openId) {
        //校验
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId is null");
        }
        //查询member表
        FamilyMember familyMember = familyMemberService.selectByOpenId(openId);
        //查询结果为空则表明未创建/加入家庭, 直接返回空
        if (familyMember == null) {
            return null;
        }
        //查询family表
        String familyId = familyMember.getFamilyId();
        Family family = familyMapper.selectByFamilyId(familyId);
        List<FamilyMember> members = familyMemberService.selectByFamilyId(familyId);

        //查询user表, 根据openId查询user的nickName和age
        List<String> openIds = members.stream().map(m -> m.getOpenId()).collect(Collectors.toList());
        Map<String, User> userMap = userService.selectUserMapByOpenIds(openIds);

        //组装memberVo
        List<FamilyMemberVo> memberVos = new ArrayList<>();
        for (FamilyMember member : members) {
            FamilyMemberVo memberVo = new FamilyMemberVo();
            memberVo.setId(member.getId());
            memberVo.setOpenId(member.getOpenId());
            memberVo.setFamilyId(familyId);
            memberVo.setFamilyIdentity(member.getFamilyIdentity());
            memberVo.setFamilyManagerMark(member.getFamilyManagerMark());
            memberVo.setCreateTime(member.getCreateTime());
            memberVo.setUpdateTime(member.getUpdateTime());
            User user = userMap.get(member.getOpenId());
            memberVo.setNickName(user.getNickName());
            memberVo.setAge(user.getAge());
            memberVos.add(memberVo);
        }

        //组装familyVo
        FamilyVo familyVo = new FamilyVo();
        familyVo.setId(family.getId());
        familyVo.setFamilyId(familyId);
        familyVo.setFamilyName(family.getFamilyName());
        familyVo.setFamilyManager(family.getFamilyManager());
        familyVo.setFamilyMemberVos(memberVos);
        familyVo.setCreateTime(family.getCreateTime());
        familyVo.setUpdateTime(family.getUpdateTime());
        return familyVo;
    }
}
