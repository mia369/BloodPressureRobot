package com.ht.bpr.service.impl;

import com.ht.bpr.common.BprResultStatus;
import com.ht.bpr.entity.LineRecord;
import com.ht.bpr.entity.PieRecord;
import com.ht.bpr.entity.Record;
import com.ht.bpr.entity.query.RecordQuery;
import com.ht.bpr.entity.vo.LineRecordVo;
import com.ht.bpr.entity.vo.PieRecordVo;
import com.ht.bpr.entity.vo.RecordVo;
import com.ht.bpr.exception.BprException;
import com.ht.bpr.mapper.RecordMapper;
import com.ht.bpr.service.RecordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void addRecord(RecordVo recordVo) {
        if (recordVo == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "recordVo is null");
        }
        if (recordVo.getOpenId() == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (recordVo.getHighBloodPressure() == null || recordVo.getLowBloodPressure() == null || recordVo.getHeartRate() == null || recordVo.getUsedPills() == null || recordVo.getMeasureTime() == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "record params are null");
        }
        Record record = RecordVo.fromVoToRecord(recordVo);
        //检查是否存在同时间的记录
        Record checkRecord = recordMapper.selectByMeasureTime(record);
        //如果存在, 则更新
        if (checkRecord != null) {
            recordMapper.update(record);
        } else {
            //如果不存在, 则添加
            Date date = new Date();
            record.setCreateTime(date);
            record.setUpdateTime(date);
            recordMapper.insert(record);
        }
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
        if (query == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "recordQuery is null");
        }
        if (StringUtils.isBlank(query.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        List<Record> records = recordMapper.select(query);
        //record转换为Vo
        List<RecordVo> recordVos = new ArrayList<>();
        if (records != null && records.size() > 0) {
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
        }
        return recordVos;
    }

    @Override
    public LineRecordVo analyzeWeek(RecordQuery query) {
        if (query == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "recordQuery is null");
        }
        if (StringUtils.isBlank(query.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (StringUtils.isBlank(query.getStartTime())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "startTime is null");
        }
        List<LineRecord> lineWeekRecords = recordMapper.selectRange(query.getOpenId(), query.getStartTime());
        if (lineWeekRecords == null) {
            return null;
        }
        LineRecordVo lineWeek = LineRecordVo.fromRecordToVo(lineWeekRecords);
        return lineWeek;
    }

    @Override
    public LineRecordVo analyzeMonth(RecordQuery query) {
        if (query == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "recordQuery is null");
        }
        if (StringUtils.isBlank(query.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (StringUtils.isBlank(query.getStartTime())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "startTime is null");
        }
        List<LineRecord> lineMonthRecords = recordMapper.selectDay(query.getOpenId(), query.getStartTime());
        if (lineMonthRecords == null) {
            return null;
        }
        LineRecordVo lineMonth = LineRecordVo.fromRecordToVo(lineMonthRecords);
        return lineMonth;
    }

    @Override
    public LineRecordVo analyzeYear(RecordQuery query) {
        if (query == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "recordQuery is null");
        }
        if (StringUtils.isBlank(query.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (StringUtils.isBlank(query.getStartTime())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "startTime is null");
        }
        List<LineRecord> lineYearRecords = recordMapper.selectMonth(query.getOpenId(), query.getStartTime());
        if (lineYearRecords == null) {
            return null;
        }
        LineRecordVo lineYear = LineRecordVo.fromRecordToVo(lineYearRecords);
        return lineYear;
    }

    @Override
    public PieRecordVo analyzeDistribution(RecordQuery query) {
        if (query == null) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "recordQuery is null");
        }
        if (StringUtils.isBlank(query.getOpenId())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "openId is null");
        }
        if (StringUtils.isBlank(query.getStartTime())) {
            throw new BprException(BprResultStatus.PARAM_IS_NULL, "startTime is null");
        }
        //获取数据
        List<PieRecord> highRecords = recordMapper.selectHighBloodPressureLevel(query.getOpenId(), query.getStartTime());
        List<PieRecord> lowRecords = recordMapper.selectLowBloodPressureLevel(query.getOpenId(), query.getStartTime());
        List<PieRecord> heartRecords = recordMapper.selectHeartRateLevel(query.getOpenId(), query.getStartTime());
        //处理数据
        for (PieRecord highRecord : highRecords) {
            PieRecord.formatDetail(highRecord);
        }
        for (PieRecord lowRecord : lowRecords) {
            PieRecord.formatDetail(lowRecord);
        }
        for (PieRecord heartRecord : heartRecords) {
            PieRecord.formatDetail(heartRecord);
        }
        //组装vo并返回
        PieRecordVo pieRecordVo = new PieRecordVo();
        pieRecordVo.setPieHighBloodPressure(highRecords);
        pieRecordVo.setPieLowBloodPressure(lowRecords);
        pieRecordVo.setPieHeartRate(heartRecords);
        return pieRecordVo;
    }

    private String getEarlyDate(Calendar calendar, int days) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - days, 0, 0, 0);
        long time = calendar.getTime().getTime();
        String earlyDate = formatter.format(new Date(Long.parseLong(String.valueOf(time))));
        return earlyDate;
    }

    private List<Integer> getIntegerList(String str) {
        List<Integer> list = new ArrayList<>();
        String[] splits = str.split(",");
        for (String split : splits) {
            Integer number = Integer.parseInt(split);
            list.add(number);
        }
        return list;
    }

    private List<String> getStringList(String str) {
        List<String> list = new ArrayList<>();
        String[] splits = str.split(",");
        for (String split : splits) {
            list.add(split);
        }
        return list;
    }


}
