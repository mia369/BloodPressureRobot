package com.ht.bpr.entity.query;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 15:38
 * @description
 */
@Data
public class RecordQuery {
    private String openId;
    private String startTime;
    private String endTime;
}

