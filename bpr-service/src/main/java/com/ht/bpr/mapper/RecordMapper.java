package com.ht.bpr.mapper;

import com.ht.bpr.entity.Record;
import com.ht.bpr.entity.query.RecordQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:37
 * @description
 */
@Mapper
public interface RecordMapper {
    void insert(Record record);

    Record selectByPk(@Param("recordPk") Integer recordPk);

    void deleteByPk(@Param("recordPk") Integer recordPk);

    List<Record> select(@Param("query") RecordQuery query);
}
