package com.ht.hhr.mapper;

import com.ht.hhr.entity.Record;
import com.ht.hhr.entity.query.RecordQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:37
 * @description
 */
public interface RecordMapper {
    void insert(Record record);

    Record selectByPk(@Param("recordPk") Integer recordPk);

    void deleteByPk(@Param("recordPk") Integer recordPk);

    List<Record> select(@Param("query") RecordQuery query);
}
