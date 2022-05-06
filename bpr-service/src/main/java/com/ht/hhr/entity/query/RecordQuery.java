package com.ht.hhr.entity.query;

import lombok.Data;

import java.util.Date;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 15:38
 * @description
 */
@Data
public class RecordQuery {
    private Integer pageNum;
    private Integer pageSize;
    private String sortOrder;
    private String startTime;
    private String endTime;


}
