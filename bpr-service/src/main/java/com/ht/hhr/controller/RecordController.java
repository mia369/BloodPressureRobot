package com.ht.hhr.controller;

import com.ht.hhr.common.Result;
import com.ht.hhr.entity.Record;
import com.ht.hhr.entity.query.RecordQuery;
import com.ht.hhr.entity.vo.RecordVo;
import com.ht.hhr.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/3 18:00
 * @description
 */
@RestController
@RequestMapping("record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("select")
    public List<RecordVo> select(@RequestBody RecordQuery query) {
        List<RecordVo> recordVos = recordService.select(query);
        return recordVos;
    }

    @PostMapping("add")
    public Result add(@RequestBody RecordVo recordVo) {
        recordService.addRecord(recordVo);
        return Result.success();
    }

    @GetMapping("delete")
    public Result delete(Integer recordPk) {
        recordService.deleteRecord(recordPk);
        return Result.success();
    }



}
