package com.ht.bpr.mapper;

import com.ht.bpr.entity.FamilyMember;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<FamilyMember> selectByFamilyId(@Param("familyId") String familyId);

    void deleteByFamilyId(@Param("familyId") String familyId);

    FamilyMember selectByFamilyIdAndOpenId(@Param("familyId") String familyId, @Param("openId") String openId);

    void deleteByFamilyIdAndOpenId(@Param("familyId") String familyId, @Param("openId") String openId);

    void updateFamilyIdentity(@Param("familyId") String familyId, @Param("openId") String openId, @Param("newIdentity") String newIdentity);
}
