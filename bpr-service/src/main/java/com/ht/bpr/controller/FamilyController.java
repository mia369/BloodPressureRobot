package com.ht.bpr.controller;

import com.ht.bpr.common.Result;
import com.ht.bpr.entity.vo.FamilyMemberVo;
import com.ht.bpr.entity.vo.FamilyVo;
import com.ht.bpr.service.FamilyMemberService;
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
    public Result<FamilyVo> addFamily(@RequestBody FamilyVo familyVo) {
        FamilyVo vo = familyService.addFamily(familyVo);
        return Result.success(vo);
    }

    @GetMapping("search")
    public Result<FamilyVo> search(String openId) {
        FamilyVo familyVo = familyService.selectByOpenId(openId);
        return Result.success(familyVo);
    }

    @PostMapping("addMember")
    public Result<FamilyVo> addMember(@RequestBody FamilyMemberVo familyMemberVo) {
        FamilyVo familyVo = familyService.addMember(familyMemberVo);
        return Result.success(familyVo);
    }

    @DeleteMapping("deleteFamily")
    public Result deleteFamily(@RequestBody FamilyVo familyVo) {
        familyService.deleteFamily(familyVo);
        return Result.success();
    }

    @DeleteMapping("deleteMember")
    public Result<FamilyVo> deleteMember(@RequestBody FamilyMemberVo memberVo) {
        FamilyVo familyVo = familyService.deleteMember(memberVo);
        return Result.success(familyVo);
    }

    @DeleteMapping("exitFamily")
    public Result exitFamily(@RequestBody FamilyMemberVo memberVo) {
        familyService.exitFamily(memberVo);
        return Result.success();
    }

    @PutMapping("updateFamilyIdentity")
    public Result<FamilyVo> updateFamilyIdentity(@RequestBody FamilyMemberVo memberVo) {
        FamilyVo familyVo = familyService.updateFamilyIdentity(memberVo);
        return Result.success(familyVo);
    }

}
