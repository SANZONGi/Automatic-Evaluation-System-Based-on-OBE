package com.sanzong.obe.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sanzong.obe.entity.Curriculum;
import com.sanzong.obe.entity.StudentModel;
import com.sanzong.obe.entity.User;
import com.sanzong.obe.entity.model.UserToCurriculumModel;
import com.sanzong.obe.service.ICurriculumService;
import com.sanzong.obe.service.IFileService;
import com.sanzong.obe.service.IUserService;
import com.sanzong.obe.service.IUserToCurriculumService;
import com.sanzong.obe.utils.MultipartFileToFileUtils;
import com.sanzong.obe.utils.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/obe/cur")
public class CurriculumController {
    @Autowired
    ICurriculumService curriculumService;

    @Autowired
    IUserToCurriculumService userToCurriculumService;

    @Autowired
    IUserService userService;

    @Autowired
    IFileService excelFileService;

    @Autowired
    private MongoTemplate mongoTemplate;

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
    public ResponseBody getCurriculumList() {
        return new ResponseBody("success", curriculumService.list(), null);
    }

    @GetMapping("/students/{id}")
    public ResponseBody getStudents(@PathVariable String id) {
        return new ResponseBody("success",
                userService.list(
                        new QueryWrapper<User>()
                                .select("id", "name", "account", "email", "phone")
                                .in("id", userToCurriculumService
                                .list(new QueryWrapper<UserToCurriculumModel>()
                                        .eq("curriculum_id", id))
                                .stream()
                                .map(UserToCurriculumModel::getUserId)
                                .collect(Collectors.toCollection(ArrayList::new))

                        )
                )
        );
    }

    @PostMapping("/excel")
    public ResponseBody uploadExcel(@RequestParam("file") MultipartFile file)  {
        try {
            excelFileService.NoModelDataRead(MultipartFileToFileUtils.multipartFileToFile(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @GetMapping("/score/{id}")
    public ResponseBody getScoreByCurriculum(@PathVariable String id) {
        Query qurey = new Query(Criteria.where("curId").is(id));
        List<StudentModel> studentModels = mongoTemplate.find(qurey, StudentModel.class);
        return new ResponseBody("success", studentModels);
    }
}