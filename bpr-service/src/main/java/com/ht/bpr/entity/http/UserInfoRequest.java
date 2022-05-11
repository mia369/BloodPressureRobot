package com.ht.bpr.entity.http;

import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/7 15:47
 * @description
 */
@Data
public class UserInfoRequest {
    private String openId;
    private String nickName;
    private String avatarUrl;
}
