package com.ht.hhr.service;

import com.ht.hhr.entity.Record;
import com.ht.hhr.entity.query.RecordQuery;
import com.ht.hhr.entity.vo.RecordVo;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:33
 * @description
 */
public interface RecordService {

    void addRecord(com.ht.hhr.entity.vo.RecordVo recordVo);

    void deleteRecord(Integer recordPk);

    List<RecordVo> select(RecordQuery query);
}
