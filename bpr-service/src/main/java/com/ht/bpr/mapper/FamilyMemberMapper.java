package com.ht.bpr.mapper;

import com.ht.bpr.entity.FamilyMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:26
 * @description
 */
@Mapper
public interface FamilyMemberMapper {
    FamilyMember selectByOpenId(@Param("openId") String openId);

    void add(@Param("member") FamilyMember Member);
}