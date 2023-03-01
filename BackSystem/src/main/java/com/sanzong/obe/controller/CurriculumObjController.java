package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sanzong.obe.entity.CurriculumObj;
import com.sanzong.obe.entity.model.AssignmentToCurriculumObjModel;
import com.sanzong.obe.service.IAssignmentToCurriculumObjService;
import com.sanzong.obe.service.ICurriculumObjService;
import com.sanzong.obe.utils.PermissionContent;
import com.sanzong.obe.utils.ResponseBody;
import com.sanzong.obe.utils.annotations.PermissionRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/cur_obj")
public class CurriculumObjController {
    @Autowired
    private ICurriculumObjService curriculumObjService;
    @Autowired
    private IAssignmentToCurriculumObjService assignmentToCurriculumObjService;

    @PermissionRequired(role = {PermissionContent.TRAIN_PROGRAM_WRITE})
    @PostMapping("/edit")
    public ResponseBody editCurriculumList(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String obj = jsonObject.getStr("obj");
        String description = jsonObject.getStr("description");
        Integer curriculumId = jsonObject.getInt("curriculumId");
        CurriculumObj curriculumObj = new CurriculumObj(id, obj, description, curriculumId);
        curriculumObjService.saveOrUpdate(curriculumObj);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list/{curriculumId}")
    public ResponseBody getCurriculumObjList(@PathVariable int curriculumId) {
        return new ResponseBody("success", curriculumObjService.list(new QueryWrapper<CurriculumObj>().eq("curriculum_id", curriculumId)), null);
    }

    @GetMapping("/matrix/{curriculumId}")
    public ResponseBody getMatrix(@PathVariable int curriculumId) {
        return new ResponseBody("success", curriculumObjService.getMatrix(curriculumId));
    }


    @PostMapping("/matrix/updateWeight")
    public ResponseBody updateWeight(@RequestBody JSONObject jsonObject) {
        int curriculumObjId = jsonObject.getInt("curriculumObjId");
        int assignmentId = jsonObject.getInt("assignmentId");
        int weight = jsonObject.getInt("weight");
        AssignmentToCurriculumObjModel model = new AssignmentToCurriculumObjModel(assignmentId, curriculumObjId, weight);

        if (assignmentToCurriculumObjService.count(new QueryWrapper<AssignmentToCurriculumObjModel>().eq("curriculum_obj_id", curriculumObjId)
                .eq("assignment_id", assignmentId)) > 0) {
            assignmentToCurriculumObjService.update(
                    new UpdateWrapper<AssignmentToCurriculumObjModel>()
                            .set("weight", weight)
                            .eq("curriculum_obj_id", curriculumObjId)
                            .eq("assignment_id", assignmentId)
            );
        } else {
            assignmentToCurriculumObjService.save(model);
        }
        return new ResponseBody("success", null);
    }

    @GetMapping("matrix/achievement/{id}")
    public ResponseBody getCurriculumAchievement(@PathVariable Integer id) {
        return new ResponseBody("success", curriculumObjService.getCurriculumAchievement(id));
    }
}
