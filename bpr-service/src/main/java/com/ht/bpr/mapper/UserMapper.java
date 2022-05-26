package com.ht.bpr.mapper;

import com.ht.bpr.entity.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 19:58
 * @description
 */
@Mapper
public interface UserMapper {
    void insert(User user);

    User selectByOpenId(@Param("openId") String openId);

    void updateUserInfo(@Param("user") User user);

    void updateUserDetails(@Param("user") User user);

    @MapKey("openId")
    Map<String, User> selectUserMapByOpenIds(@Param("openIds") List<String> openIds);
}
