package com.ht.bpr.entity.http;

import com.ht.bpr.entity.User;
import com.ht.bpr.entity.vo.FamilyVo;
import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/6 23:45
 * @description
 */
@Data
public class LoginResponse {
    private String openId;
    private String sessionKey;
    private User userInfo;
    private FamilyVo familyInfo;
}
