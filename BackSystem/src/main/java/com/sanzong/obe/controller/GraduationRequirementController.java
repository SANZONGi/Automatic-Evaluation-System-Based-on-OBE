package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanzong.obe.entity.GraduationRequirement;
import com.sanzong.obe.entity.TrainingObjective;
import com.sanzong.obe.service.impl.GraduationRequirementServiceImpl;
import com.sanzong.obe.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/gradreqs")
public class GraduationRequirementController{
    @Autowired
    GraduationRequirementServiceImpl graduationRequirementService;


    @PostMapping("/edit")
    public ResponseBody editProgram(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String name = jsonObject.getStr("name");
        String content = jsonObject.getStr("content");
        GraduationRequirement graduationRequirement = new GraduationRequirement(id, name, content);
        graduationRequirementService.saveOrUpdate(graduationRequirement);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list")
    public ResponseBody getRequirementList() {
        return new ResponseBody("success", graduationRequirementService.list(), null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseBody deleteRequirement(@PathVariable Integer id) {
        graduationRequirementService.removeById(id);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/{id}")
    public ResponseBody getRequirementById(@PathVariable Integer id) {
        return new ResponseBody("success", graduationRequirementService.getById(id), null);
    }
}
