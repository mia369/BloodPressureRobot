package com.ht.bpr.entity.vo;

import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/7 15:53
 * @description
 */
@Data
public class UserVo {
    private Integer id;
    //UserInfo
    private String openId;
    private String nickName;
    private String avatarUrl;
    //UserDetails
    private Integer age;
    private Integer height;
    private Integer weight;
    //OtherInfo
    private String unionId;
    private String lastRecordTime;
}
