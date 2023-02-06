package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.sanzong.obe.entity.Curriculum;
import com.sanzong.obe.entity.CurriculumObj;
import com.sanzong.obe.service.ICurriculumObjService;
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
@RequestMapping("/obe/cur_obj")
public class CurriculumObjController{
    @Autowired
    ICurriculumObjService curriculumObjService;

    @PostMapping("/edit")
    public ResponseBody editCurriculumList(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String obj = jsonObject.getStr("obj");
        String description = jsonObject.getStr("description");
        CurriculumObj curriculumObj = new CurriculumObj(id, obj, description);
        curriculumObjService.saveOrUpdate(curriculumObj);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list")
    public ResponseBody getCurriculumList() {
        return new ResponseBody("success", curriculumObjService.list(), null);
    }
}
