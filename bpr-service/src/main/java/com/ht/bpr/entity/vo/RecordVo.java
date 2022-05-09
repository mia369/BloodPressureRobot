package com.ht.bpr.entity.vo;

import com.ht.bpr.entity.Record;
import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 21:21
 * @description
 */
@Data
public class RecordVo {
    private Integer id;
    private String openId;
    private Integer highBloodPressure;
    private Integer lowBloodPressure;
    private Integer heartRate;
    private Integer usedPills;
    private String measureTime;

    public static Record fromVoToRecord(RecordVo recordVo) {
        Record record = new Record();
        record.setOpenId(recordVo.getOpenId());
        record.setHighBloodPressure(recordVo.getHighBloodPressure());
        record.setLowBloodPressure(recordVo.getLowBloodPressure());
        record.setHeartRate(recordVo.getHeartRate());
        record.setUsedPills(recordVo.getUsedPills());
        record.setMeasureTime(recordVo.getMeasureTime());
        return record;
    }
}
