package com.ht.bpr.controller;

import com.ht.bpr.common.Result;
import com.ht.bpr.entity.query.RecordQuery;
import com.ht.bpr.entity.vo.ChartVo;
import com.ht.bpr.entity.vo.LineRecordVo;
import com.ht.bpr.entity.vo.PieRecordVo;
import com.ht.bpr.entity.vo.RecordVo;
import com.ht.bpr.service.RecordService;
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

    @PostMapping("select")
    public Result<List<RecordVo>> select(@RequestBody RecordQuery query) {
        List<RecordVo> recordVos = recordService.select(query);
        return Result.success(recordVos);
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

    @PostMapping("analyze")
    public Result<ChartVo> analyze(@RequestBody RecordQuery query) {
        ChartVo chartVo = recordService.analyzeRecord(query);
        return Result.success(chartVo);
    }

    @PostMapping("analyzeWeek")
    public Result<LineRecordVo> analyzeWeek(@RequestBody RecordQuery query) {
        LineRecordVo lineWeek = recordService.analyzeWeek(query);
        return Result.success(lineWeek);
    }

    @PostMapping("analyzeMonth")
    public Result<LineRecordVo> analyzeMonth(@RequestBody RecordQuery query) {
        LineRecordVo lineMonth = recordService.analyzeMonth(query);
        return Result.success(lineMonth);
    }

    @PostMapping("analyzeYear")
    public Result<LineRecordVo> analyzeYear(@RequestBody RecordQuery query) {
        LineRecordVo lineYear = recordService.analyzeYear(query);
        return Result.success(lineYear);
    }

    @PostMapping("analyzeDistribution")
    public Result<PieRecordVo> analyzeDistribution(@RequestBody RecordQuery query) {
        PieRecordVo pieRecordVo = recordService.analyzeDistribution(query);
        return Result.success(pieRecordVo);
    }
}
