package com.ht.hhr.service.impl;

import com.github.pagehelper.PageHelper;
import com.ht.hhr.entity.MeasurePeriod;
import com.ht.hhr.entity.Record;
import com.ht.hhr.entity.query.RecordQuery;
import com.ht.hhr.entity.vo.RecordVo;
import com.ht.hhr.mapper.RecordMapper;
import com.ht.hhr.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:33
 * @description
 */
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public void addRecord(RecordVo recordVo) {
        Record record = RecordVo.fromVoToRecord(recordVo);
        recordMapper.insert(record);
    }

    @Override
    public void deleteRecord(Integer recordPk) {
        Record record = recordMapper.selectByPk(recordPk);
        if (record != null) {
            recordMapper.deleteByPk(recordPk);
        }
    }

    @Override
    public List<RecordVo> select(RecordQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<Record> records = recordMapper.select(query);
        //record转换为Vo
        if(records != null && records.size() > 0) {
            List<RecordVo> recordVos = new ArrayList<>();
            for (Record record : records) {
                RecordVo recordVo = new RecordVo();
                recordVo.setId(record.getId());
                recordVo.setOpenId(record.getOpenId());
                recordVo.setHighBloodPressure(record.getHighBloodPressure());
                recordVo.setLowBloodPressure(record.getLowBloodPressure());
                recordVo.setHeartRate(record.getHeartRate());
                recordVo.setMeasureTime(record.getMeasureTime());
                recordVos.add(recordVo);
            }
            return recordVos;
        }
        return null;
    }

}
