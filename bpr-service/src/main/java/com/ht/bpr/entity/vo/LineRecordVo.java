package com.ht.bpr.entity.vo;

import com.ht.bpr.entity.LineRecord;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/18 15:22
 * @description
 */
@Data
public class LineRecordVo {
    private List<Integer> avgHighBloodPressure;
    private List<Integer> avgLowBloodPressure;
    private List<Integer> avgHeartRate;
    private List<Integer> maxHighBloodPressure;
    private List<Integer> maxLowBloodPressure;
    private List<Integer> maxHeartRate;
    private List<String> measureTime;

    public static LineRecordVo fromRecordListToVo(List<LineRecord> records) {
        LineRecordVo recordVo = new LineRecordVo();
        List<Integer> avgHighBloodPressure = new ArrayList<>();
        List<Integer> avgLowBloodPressure = new ArrayList<>();
        List<Integer> avgHeartRate = new ArrayList<>();
        List<Integer> maxHighBloodPressure = new ArrayList<>();
        List<Integer> maxLowBloodPressure = new ArrayList<>();
        List<Integer> maxHeartRate = new ArrayList<>();
        List<String> measureTime = new ArrayList<>();
        for (LineRecord record : records) {
            avgHighBloodPressure.add(record.getAvgHighBloodPressure());
            avgLowBloodPressure.add(record.getAvgLowBloodPressure());
            avgHeartRate.add(record.getAvgHeartRate());
            maxHighBloodPressure.add(record.getMaxHighBloodPressure());
            maxLowBloodPressure.add(record.getMaxLowBloodPressure());
            maxHeartRate.add(record.getMaxHeartRate());
            if (record.getMeasureRange() != null) {
                measureTime.add(record.getMeasureRange());
            }else if (record.getMeasureDay() != null) {
                measureTime.add(record.getMeasureDay());
            }else if (record.getMeasureMonth() != null) {
                measureTime.add(record.getMeasureMonth());
            }
        }
        recordVo.setAvgHighBloodPressure(avgHighBloodPressure);
        recordVo.setAvgLowBloodPressure(avgLowBloodPressure);
        recordVo.setAvgHeartRate(avgHeartRate);
        recordVo.setMaxHighBloodPressure(maxHighBloodPressure);
        recordVo.setMaxLowBloodPressure(maxLowBloodPressure);
        recordVo.setMaxHeartRate(maxHeartRate);
        recordVo.setMeasureTime(measureTime);
        return recordVo;
    }
}
