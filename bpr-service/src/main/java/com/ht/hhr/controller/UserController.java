package com.ht.hhr.controller;

import com.alibaba.fastjson.JSONObject;
import com.ht.hhr.common.Result;
import com.ht.hhr.config.WeChatConfig;
import com.ht.hhr.entity.LoginRequest;
import com.ht.hhr.entity.LoginResponse;
import com.ht.hhr.entity.User;
import com.ht.hhr.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 18:00
 * @description
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WeChatConfig weChatConfig;

    @PostMapping("insert")
    public String insertUser(@RequestBody User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        return "添加成功";
    }

    @PostMapping("login")
    public Result appletsLogin(@RequestBody LoginRequest loginRequest) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", weChatConfig.getAppId());
        requestMap.put("secret", weChatConfig.getSecret());
        requestMap.put("code", loginRequest.getCode());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, requestMap);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        LoginResponse loginResponse = new LoginResponse();
        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");
        loginResponse.setOpenId(openId);
        loginResponse.setSessionKey(sessionKey);
        return Result.success(loginResponse);
    }
}
