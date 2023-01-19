package com.sanzong.obe.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.sanzong.obe.entity.TrainingProgram;
import com.sanzong.obe.service.impl.TrainingProgramServiceImpl;
import com.sanzong.obe.utils.ResponseBody;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/program")
public class TrainingProgramController{
    @Autowired
    TrainingProgramServiceImpl trainingProgramService;

    @PostMapping("/edit")
    public ResponseBody editProgram(@RequestBody JSONObject jsonObject) {
        String name = jsonObject.getStr("name");
        String grad = jsonObject.getStr("grad");
        String description = jsonObject.getStr("description");
        Integer id = jsonObject.getInt("id");
        TrainingProgram trainingProgram = new TrainingProgram(id, name, grad, description);
        trainingProgramService.saveOrUpdate(trainingProgram);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list")
    public ResponseBody getProgramList() {
        return new ResponseBody("success", trainingProgramService.list(), null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseBody deleteProgram(@PathVariable Integer id) {
        trainingProgramService.removeById(id);
        return new ResponseBody("success", 1, null);
    }
}
