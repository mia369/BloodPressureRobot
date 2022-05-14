package com.ht.bpr.service.impl;

import com.github.pagehelper.PageHelper;
import com.ht.bpr.entity.Record;
import com.ht.bpr.entity.query.RecordQuery;
import com.ht.bpr.entity.vo.RecordVo;
import com.ht.bpr.mapper.RecordMapper;
import com.ht.bpr.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/4 10:33
 * @description
 */
@Service
public class RecordServiceImpl implements RecordService {

    private static final Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

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
