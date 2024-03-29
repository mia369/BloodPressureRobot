package com.ht.bpr.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/7 15:53
 * @description
 */
@Data
public class UserVo {
    private Integer id;
    private String openId;
    private String nickName;
    private String avatarUrl;
    private Integer age;
    private Integer height;
    private Integer weight;
    private String unionId;
    private String lastRecordTime;
    private Date createTime;
    private Date updateTime;
}
