package com.ht.bpr.service;

import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 20:18
 * @description
 */
public interface UserService {

    User selectOne(String openId);

    void saveUserInfo(UserVo userVo);

    void saveUserDetails(UserVo userVo);

    Map<String, User> selectUserMapByOpenIds(List<String> openIds);
}
