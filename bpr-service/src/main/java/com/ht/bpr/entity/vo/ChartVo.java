package com.ht.bpr.entity.vo;

import lombok.Data;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/22 9:50
 * @description
 */
@Data
public class ChartVo {
    private LineRecordVo lineWeek;
    private LineRecordVo lineMonth;
    private LineRecordVo lineYear;
    private PieRecordVo pieHigh;
    private PieRecordVo pieLow;
    private PieRecordVo pieHeart;
}
