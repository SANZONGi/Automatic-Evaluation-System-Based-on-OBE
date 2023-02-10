package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.sanzong.obe.entity.Curriculum;
import com.sanzong.obe.service.ICurriculumService;
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
@RequestMapping("/obe/cur")
public class CurriculumController{
    @Autowired
    ICurriculumService curriculumService;


    @PostMapping("/edit")
    public ResponseBody editCurriculumList(@RequestBody JSONObject jsonObject) {
        Integer id = jsonObject.getInt("id");
        String name = jsonObject.getStr("name");
        String teacher = jsonObject.getStr("teacher");
        String open = jsonObject.getStr("open");
        String outline = jsonObject.getStr("outline");
        String criterion = jsonObject.getStr("criterion");
        Curriculum curriculum = new Curriculum(id, name, teacher, open, outline, criterion);
        curriculumService.saveOrUpdate(curriculum);
        return new ResponseBody("success", 1, null);
    }

    @GetMapping("/list")
    public  ResponseBody getCurriculumList() {
        return new ResponseBody("success", curriculumService.list(), null);
    }
}
