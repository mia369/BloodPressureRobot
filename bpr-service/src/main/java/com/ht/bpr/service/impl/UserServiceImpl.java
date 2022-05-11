package com.ht.bpr.service.impl;

import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.UserVo;
import com.ht.bpr.mapper.UserMapper;
import com.ht.bpr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 20:18
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectOne(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openId is null");
        }
        User user = userMapper.selectOne(openId);
        return user;
    }

    @Override
    public void add(User user) {
        if (StringUtils.isBlank(user.getOpenId())) {
            throw new RuntimeException("openId is null");
        }
        if (StringUtils.isBlank(user.getNickName())) {
            throw new RuntimeException("nickName is null");
        }
        if (StringUtils.isBlank(user.getAvatarUrl())) {
            throw new RuntimeException("avatarUrl is null");
        }
        userMapper.insert(user);
    }

    @Override
    public void saveUserInfo(UserVo userVo) {
        User user = new User();
        user.setOpenId(userVo.getOpenId());
        user.setNickName(userVo.getNickName());
        user.setAvatarUrl(userVo.getAvatarUrl());
        //查询用户是否存在
        User checkUser = selectOne(userVo.getOpenId());
        if (checkUser == null) {
            // 注册
            add(user);
        } else {
            //更新
            updateUserInfo(user);
        }
    }

    @Override
    public void saveUserDetails(UserVo userVo) {
        if (userVo.getOpenId() == null) {
            throw new RuntimeException("openId is null");
        }
        User user = new User();
        user.setOpenId(userVo.getOpenId());
        user.setNickName(userVo.getNickName());
        user.setAge(userVo.getAge());
        user.setHeight(userVo.getHeight());
        user.setWeight(userVo.getWeight());
        userMapper.updateUserDetails(user);
    }

    private void updateUserInfo(User user) {
        if (StringUtils.isBlank(user.getOpenId())) {
            throw new RuntimeException("openId is null");
        }
        if (StringUtils.isBlank(user.getNickName())) {
            throw new RuntimeException("nickName is null");
        }
        if (StringUtils.isBlank(user.getAvatarUrl())) {
            throw new RuntimeException("avatarUrl is null");
        }
        userMapper.updateUserInfo(user);
    }


}