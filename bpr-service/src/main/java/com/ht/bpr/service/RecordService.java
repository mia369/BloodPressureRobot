package com.ht.bpr.service;

import com.ht.bpr.entity.query.RecordQuery;
import com.ht.bpr.entity.vo.RecordVo;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:33
 * @description
 */
public interface RecordService {

    void addRecord(com.ht.bpr.entity.vo.RecordVo recordVo);

    void deleteRecord(Integer recordPk);

    List<RecordVo> select(RecordQuery query);
}
