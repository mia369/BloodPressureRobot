package com.ht.bpr.mapper;

import com.ht.bpr.entity.Family;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:26
 * @description
 */
@Mapper
public interface FamilyMapper {
    void add(@Param("family") Family family);

    Family selectByFamilyId(@Param("familyId") String familyId);

    List<Family> selectBatchByFamilyIds(@Param("familyIds") List<String> familyIds);
}
