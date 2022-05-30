package com.ht.bpr.controller;

import com.alibaba.fastjson.JSONObject;
import com.ht.bpr.common.Result;
import com.ht.bpr.config.WeChatConfig;
import com.ht.bpr.entity.http.UserInfoRequest;
import com.ht.bpr.entity.http.LoginRequest;
import com.ht.bpr.entity.http.LoginResponse;
import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.entity.vo.UserVo;
import com.ht.bpr.service.FamilyService;
import com.ht.bpr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private UserService userService;
    @Autowired
    private FamilyService familyService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 启动小程序时静默登录
     * @param loginRequest  wx.login()成功响应结果中的code
     * @return Result   返回openId和sessionKey
     */
    @PostMapping("login")
    public Result login(@RequestBody LoginRequest loginRequest) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("appid", weChatConfig.getAppId());
        requestMap.put("secret", weChatConfig.getSecret());
        requestMap.put("code", loginRequest.getCode());

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, requestMap);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        String openId = jsonObject.getString("openid");
        String sessionKey = jsonObject.getString("session_key");

        User user = userService.selectOne(openId);
        FamilyVo familyVo = familyService.selectByOpenId(openId);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setOpenId(openId);
        loginResponse.setSessionKey(sessionKey);
        loginResponse.setUserInfo(user);
        loginResponse.setFamilyInfo(familyVo);
        return Result.success(loginResponse);
    }

    @GetMapping("search")
    public Result<User> searchOne(String openId) {
        User user = userService.selectOne(openId);
        return Result.success(user);
    }

    @PostMapping("saveUserInfo")
    public Result saveUserInfo(@RequestBody UserVo userVo) {
        userService.saveUserInfo(userVo);
        return Result.success();
    }

    @PostMapping("saveUserDetails")
    public Result saveUserDetails(@RequestBody UserVo userVo) {
        userService.saveUserDetails(userVo);
        return Result.success();
    }
}
