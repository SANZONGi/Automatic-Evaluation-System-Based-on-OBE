package com.sanzong.obe.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanzong.obe.entity.Assignment;
import com.sanzong.obe.entity.Curriculum;
import com.sanzong.obe.service.IAssignmentService;
import com.sanzong.obe.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author SANZONG
 */
@RestController
@RequestMapping("/obe/cur/assignment")
public class AssignmentController {
    @Autowired
    private IAssignmentService assignmentService;

    @GetMapping("/list/{id}")
    public ResponseBody getAssignmentList(@PathVariable int id) {
        return new ResponseBody("success", assignmentService.list(new QueryWrapper<Assignment>().eq("curriculum_id", id)), null);
    }

    @PostMapping("/edit")
    public ResponseBody editAssignment(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String name = jsonObject.getStr("name");
        Integer curriculumId = jsonObject.getInt("curriculumId");
        Assignment assignment = new Assignment(id, name, curriculumId);
        assignmentService.saveOrUpdate(assignment);
        return new ResponseBody("success", 1, null);
    }

}
