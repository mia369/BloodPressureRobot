package com.ht.bpr.controller;

import com.alibaba.fastjson.JSONObject;
import com.ht.bpr.common.Result;
import com.ht.bpr.entity.vo.FamilyMemberVo;
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

    @PostMapping("addFamily")
    public Result addFamily(@RequestBody JSONObject jsonObject) {
        String familyName = jsonObject.getString("familyName");
        String openId = jsonObject.getString("openId");
        familyService.addFamily(familyName, openId);
        return Result.success();
    }

    @GetMapping("search")
    public Result<FamilyVo> search(String openId) {
        FamilyVo familyVo = familyService.selectByOpenId(openId);
        return Result.success(familyVo);
    }

    @PostMapping("addMember")
    public Result addMember(@RequestBody FamilyMemberVo familyMemberVo) {
        familyService.addMember(familyMemberVo);
        return Result.success();
    }

    @DeleteMapping("deleteFamily")
    public Result deleteFamily(@RequestBody FamilyVo familyVo) {
        familyService.deleteFamily(familyVo);
        return Result.success();
    }

    @DeleteMapping("deleteMember")
    public Result deleteMember(@RequestBody FamilyMemberVo memberVo) {
        familyService.deleteMember(memberVo);
        return Result.success();
    }

    @DeleteMapping("exitFamily")
    public Result exitFamily(@RequestBody FamilyMemberVo memberVo) {
        familyService.exitFamily(memberVo);
        return Result.success();
    }

    @PutMapping("updateFamilyIdentity")
    public Result updateFamilyIdentity(@RequestBody FamilyMemberVo memberVo) {
        familyService.updateFamilyIdentity(memberVo);
        return Result.success();
    }

}
