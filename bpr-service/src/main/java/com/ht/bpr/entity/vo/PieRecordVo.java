package com.ht.bpr.entity.vo;

import com.ht.bpr.entity.PieRecord;
import lombok.Data;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/21 10:04
 * @description
 */
@Data
public class PieRecordVo {
    private List<PieRecord> pieHighBloodPressure;
    private List<PieRecord> pieLowBloodPressure;
    private List<PieRecord> pieHeartRate;
}
