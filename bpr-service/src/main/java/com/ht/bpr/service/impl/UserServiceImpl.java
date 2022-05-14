package com.ht.bpr.service.impl;

import com.ht.bpr.common.BprResultStatus;
import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.UserVo;
import com.ht.bpr.exception.BprException;
import com.ht.bpr.mapper.UserMapper;
import com.ht.bpr.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 20:18
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectOne(String openId) {
        if (StringUtils.isBlank(openId)) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        User user = userMapper.selectOne(openId);
        return user;
    }

    @Override
    public void add(User user) {
        if (StringUtils.isBlank(user.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (StringUtils.isBlank(user.getNickName())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "nickName is null");
        }
        if (StringUtils.isBlank(user.getAvatarUrl())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "avatarUrl is null");
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
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        User user = new User();
        user.setOpenId(userVo.getOpenId());
        user.setNickName(userVo.getNickName());
        user.setAge(userVo.getAge());
        user.setHeight(userVo.getHeight());
        user.setWeight(userVo.getWeight());
        userMapper.updateUserDetails(user);
    }

    @Override
    public Map<String, User> selectUserMapByOpenIds(List<String> openIds) {
        Map<String, User> userMap = new HashMap<>();
        if (openIds != null && openIds.size() > 0) {
            userMap = userMapper.selectUserMapByOpenIds(openIds);
        }
        return userMap;
    }

    private void updateUserInfo(User user) {
        if (StringUtils.isBlank(user.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (StringUtils.isBlank(user.getNickName())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "nickName is null");
        }
        if (StringUtils.isBlank(user.getAvatarUrl())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "avatarUrl is null");
        }
        userMapper.updateUserInfo(user);
    }


}
