package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.sanzong.obe.entity.GraduationRequirement;
import com.sanzong.obe.entity.SubdivisionPoint;
import com.sanzong.obe.service.impl.GraduationRequirementServiceImpl;
import com.sanzong.obe.service.impl.SubdivisionPointServiceImpl;
import com.sanzong.obe.utils.ResponseBody;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/subdivision")
public class SubdivisionPointController{
    @Autowired
    SubdivisionPointServiceImpl subdivisionPointService;
    @Autowired
    GraduationRequirementServiceImpl graduationRequirementService;


    @PostMapping("/edit")
    public ResponseBody editProgram(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        Integer fid = jsonObject.getInt("fid");
        String name = jsonObject.getStr("name");
        String content = jsonObject.getStr("content");
        SubdivisionPoint subdivisionPoint = new SubdivisionPoint(id, name, content, fid);
        subdivisionPointService.saveOrUpdate(subdivisionPoint);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list")
    public ResponseBody getRequirementList() {
        List<SubdivisionPoint> list = subdivisionPointService.list();
        JSONArray jsonArray = JSONArray.fromObject(list);
        for (int i = 0; i < jsonArray.size();i++) {
            int fid  = jsonArray.getJSONObject(i).getInt("graduationRequirementId");
            jsonArray.getJSONObject(i).replace("graduationRequirementId",
                    graduationRequirementService.getById(fid));
        }
        return new ResponseBody("success", jsonArray, null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseBody deleteRequirement(@PathVariable Integer id) {
        subdivisionPointService.removeById(id);
        return new ResponseBody("success", 1, null);
    }
}
