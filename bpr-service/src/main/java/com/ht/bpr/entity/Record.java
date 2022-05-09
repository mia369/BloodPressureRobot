package com.ht.bpr.entity;

import lombok.Data;

import java.util.Date;


/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 19:50
 * @description
 */
@Data
public class Record {
    private Integer id;
    private String openId;
    private Integer highBloodPressure;
    private Integer lowBloodPressure;
    private Integer heartRate;
    private Integer usedPills;
    private String measureTime;
    private Date createTime;
    private Date updateTime;
}
