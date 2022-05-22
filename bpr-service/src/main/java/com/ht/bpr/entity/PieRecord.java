package com.ht.bpr.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/22 10:57
 * @description
 */
@Data
public class PieRecord {
    private String name;
    private Integer value;
    private String detail;

    public static void formatDetail(PieRecord record) {
        if (StringUtils.isNotBlank(record.getDetail())) {
            record.setDetail("\n" + record.getDetail().replace(",", "\n"));
        }
    }
}
