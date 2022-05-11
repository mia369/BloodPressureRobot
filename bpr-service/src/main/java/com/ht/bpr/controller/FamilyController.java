package com.ht.bpr.controller;

import com.ht.bpr.common.Result;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Zhu Shaoqin
 * @email zsqmia@163.com
 * @date 2022/5/10 11:25
 * @description
 */
@RestController
@RequestMapping("family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @PostMapping("add")
    public Result<FamilyVo> add(@RequestBody FamilyVo familyVo) {
        FamilyVo vo = familyService.add(familyVo);
        return Result.success(vo);
    }

    @PostMapping("search")
    public Result<FamilyVo> search(String openId) {
        FamilyVo familyVo = familyService.selectByOpenId(openId);
        return Result.success(familyVo);
    }

}
