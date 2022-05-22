package com.ht.bpr.entity;

import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/21 10:25
 * @description
 */
@Data
public class LineRecord {
    private Integer avgHighBloodPressure;
    private Integer avgLowBloodPressure;
    private Integer avgHeartRate;
    private Integer maxHighBloodPressure;
    private Integer maxLowBloodPressure;
    private Integer maxHeartRate;
    private String measureRange;
    private String measureDay;
    private String measureMonth;
}
