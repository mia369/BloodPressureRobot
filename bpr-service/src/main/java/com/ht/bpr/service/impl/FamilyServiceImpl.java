package com.ht.bpr.service.impl;

import com.ht.bpr.common.Constants;
import com.ht.bpr.entity.Family;
import com.ht.bpr.entity.FamilyMember;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.mapper.FamilyMapper;
import com.ht.bpr.mapper.FamilyMemberMapper;
import com.ht.bpr.service.FamilyMemberService;
import com.ht.bpr.service.FamilyService;
import com.ht.bpr.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.Date;
import java.util.List;


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

    @Override
    public void add(FamilyVo familyVo) {
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
    }

    @Override
    public FamilyVo selectByOpenId(String openId) {
        //校验
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId is null");
        }
        //查询member表,family表
//        List<FamilyMember> members = familyMemberService.searchByOpenId(openId);
//        Family family = familyMapper.selectByOpenId(openId);

        //组装familyVo
        FamilyVo familyVo = new FamilyVo();


        return null;
    }
}
