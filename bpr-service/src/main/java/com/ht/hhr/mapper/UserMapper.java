package com.ht.hhr.mapper;

import com.ht.hhr.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 19:58
 * @description
 */
@Mapper
public interface UserMapper {
    void insert(User user);
}
