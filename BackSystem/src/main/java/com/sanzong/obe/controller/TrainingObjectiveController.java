package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanzong.obe.entity.TrainingObjective;
import com.sanzong.obe.entity.TrainingProgram;
import com.sanzong.obe.service.impl.TrainingObjectiveServiceImpl;
import com.sanzong.obe.utils.ResponseBody;
import io.swagger.models.auth.In;
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
@RequestMapping("/obe/objective")
public class TrainingObjectiveController{
    @Autowired
    TrainingObjectiveServiceImpl trainingObjectiveService;

    @PostMapping("/edit")
    public ResponseBody editProgram(@RequestBody JSONObject jsonObject) {
        String objective = jsonObject.getStr("objective");
        String content = jsonObject.getStr("content");
        Integer fatherId = jsonObject.getInt("fid");
        Integer id = jsonObject.getInt("id");
        TrainingObjective trainingObjective = new TrainingObjective(id, objective, content, fatherId);
        trainingObjectiveService.saveOrUpdate(trainingObjective);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list/{fid}")
    public ResponseBody getObjectiveList(@PathVariable Integer fid) {
        return new ResponseBody("success", trainingObjectiveService.list(new QueryWrapper<TrainingObjective>()
                .eq("training_program_id", fid)), null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseBody deleteProgram(@PathVariable Integer id) {
        trainingObjectiveService.removeById(id);
        return new ResponseBody("success", 1, null);
    }
}
