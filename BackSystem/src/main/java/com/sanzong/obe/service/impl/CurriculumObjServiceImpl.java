package com.sanzong.obe.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzong.obe.entity.Assignment;
import com.sanzong.obe.entity.CurriculumObj;
import com.sanzong.obe.entity.StudentModel;
import com.sanzong.obe.entity.model.AssignmentToCurriculumObjModel;
import com.sanzong.obe.mapper.AssignmentMapper;
import com.sanzong.obe.mapper.CurriculumObjMapper;
import com.sanzong.obe.service.IAssignmentToCurriculumObjService;
import com.sanzong.obe.service.ICurriculumObjService;
import com.sanzong.obe.service.IMongodbService;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Service
@Slf4j
public class CurriculumObjServiceImpl extends ServiceImpl<CurriculumObjMapper, CurriculumObj> implements ICurriculumObjService {
    @Autowired
    private CurriculumObjMapper curriculumObjMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IMongodbService mongodbService;
    @Autowired
    private IAssignmentToCurriculumObjService assignmentToCurriculumObjService;

    @Override
    public JSONArray getMatrix(int curriculumId) {
        // getData
        List<CurriculumObj> curriculumObjs = curriculumObjMapper.selectList(new QueryWrapper<CurriculumObj>().eq("curriculum_id", curriculumId));
        List<AssignmentToCurriculumObjModel> curriculumObjWithAssignments = curriculumObjMapper.getCurriculumObjWithAssignment();
        List<Assignment> assignments = assignmentMapper.selectList(
                new QueryWrapper<Assignment>().in(
                        "curriculum_id", curriculumObjs
                                .stream()
                                .map(CurriculumObj::getId)
                                .collect(Collectors.toCollection(ArrayList::new))
                )
        );
        // getConnectSet
        HashMap<Pair<Integer, Integer>, Integer> weights = new HashMap<>(100);
        for (AssignmentToCurriculumObjModel item : curriculumObjWithAssignments) {
            weights.put(new Pair<>(item.getCurriculumObjId(), item.getAssignmentId()), item.getWeight());
        }
        JSONArray jsonArray = new JSONArray();
        for (Assignment assignment : assignments) {
            JSONArray objArray = new JSONArray();
            for (CurriculumObj obj : curriculumObjs) {
                JSONObject jsonObject = new JSONObject();
                Pair<Integer, Integer> pair = new Pair<>(obj.getId(), assignment.getId());
                int weight = weights.getOrDefault(pair, 0);

                jsonObject.put("curriculumObj", obj);
                jsonObject.put("weight", weight);
                objArray.add(jsonObject);
            }
            JSONObject temp = new JSONObject();
            temp.put("assignment", assignment);
            temp.put("objArray", objArray);
            jsonArray.add(temp);
        }
        return jsonArray;
    }

    @Override
    public List<StudentModel> getCurriculumAchievement(int id) {
        return updateCurriculumAchievement(id);
    }

    public List<StudentModel> updateCurriculumAchievement(int id) {
        List<StudentModel> studentModels = mongoTemplate.find(new Query(Criteria.where("curId").is(String.valueOf(id))), StudentModel.class);
        Map<String, List<Pair<Integer, Integer>>> scores = new HashMap<>(100);
        List<Integer> assignments = null;
        List<CurriculumObj> curriculumObjs = null;
        List<AssignmentToCurriculumObjModel> assignmentToCurriculumObjList = null;
        Map<Integer, String> curriculumObjMap = new HashMap<>(100);
        // 获取学生学业成绩
        for (StudentModel studentModel : studentModels) {
            List<JSONObject> detail = studentModel.getDetail().toList(JSONObject.class);
            List<Pair<Integer, Integer>> scorePairList = detail
                    .stream()
                    .map(item -> new Pair<Integer, Integer>(getId(item.getString("assignment")), item.getInteger("score")))
                    .sorted(new Comparator<Pair<Integer, Integer>>() {
                        @Override
                        public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                            return o2.getKey().compareTo(o1.getKey());
                        }
                    })
                    .collect(Collectors.toList());
            if (assignments == null) {
                assignments = detail
                        .stream()
                        .map(item -> getId(item.getString("assignment")))
                        .collect(Collectors.toList());
            }
            scores.put(studentModel.getName(), scorePairList);
        }
        if (assignments == null) {
            log.error("assignments is null");
            return null;
        }
        // 获取连接表数据
        assignmentToCurriculumObjList = assignmentToCurriculumObjService.list(
                new QueryWrapper<AssignmentToCurriculumObjModel>()
                        .in("assignment_id", assignments)
        );
        // 获取课程目标
        curriculumObjs = curriculumObjMapper.selectList(
                new QueryWrapper<CurriculumObj>()
                .eq("curriculum_id", id));

        curriculumObjs.forEach(curriculumObj -> {
                    curriculumObjMap.put(curriculumObj.getId(), curriculumObj.getCurriculumObj() + "(" + curriculumObj.getId() + ")");
                });
        // 整理链接表
        Map<Integer, List<Pair<Integer, Integer> > > connectMatrix = new HashMap<>(1000);
        for (AssignmentToCurriculumObjModel model : assignmentToCurriculumObjList) {
            if (!connectMatrix.containsKey(model.getCurriculumObjId())) {
                connectMatrix.put(model.getCurriculumObjId(),new ArrayList<>());
            }
            connectMatrix
                    .get(model.getCurriculumObjId())
                    .add(new Pair<>(model.getAssignmentId(), model.getWeight()));
        }
        studentModels.clear();
        for (String stuName : scores.keySet()) {
            JSONArray curObjAchievement = new JSONArray();
            List<Pair<Integer,Integer>> scoreList = scores.get(stuName);
            // 获取课程目标达成度
            for (Integer key : connectMatrix.keySet()) {
                List<Pair<Integer, Integer>> weights = connectMatrix.get(key).stream().sorted(new Comparator<Pair<Integer, Integer>>() {
                    @Override
                    public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                        return o2.getKey().compareTo(o1.getKey());
                    }
                }).collect(Collectors.toList());
                // 双指针实现计算达成度
                int curObjId = key;
                int i = 0;
                int j = 0;
                double achievement = 0;
                JSONObject item = new JSONObject();
                while (i < scoreList.size() && j < weights.size()) {
                    if (Objects.equals(scoreList.get(i).getKey(), weights.get(j).getKey())) {
                        achievement += scoreList.get(i).getValue() * weights.get(j).getValue() / 100.0;
                        i++;
                        j++;
                    } else if (scoreList.get(i).getKey() < weights.get(i).getKey()) {
                        i++;
                    }
                    else {
                        j++;
                    }
                }
                item.put("curObj", curriculumObjMap.get(curObjId));
                item.put("achievement", achievement);
                curObjAchievement.add(item);
            }
            Update update = new Update();
            update.set("curObjAchievement", curObjAchievement);
            studentModels.add(mongodbService.updateStudentModelByColum(id, stuName, true, false, update));
        }
        return studentModels;
    }



    private Integer getId(String origin) {
        origin = origin.trim();
        int pos = 0;
        int id = 0;
        for (int i = origin.length() - 2; i >= 0; i--) {
            if (origin.charAt(i) == '(') {
                break;
            }
            id += (origin.charAt(i) - '0') * Math.pow(10, pos++);

        }
        return id;
    }
}
