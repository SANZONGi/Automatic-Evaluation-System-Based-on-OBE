package com.sanzong.obe.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzong.obe.entity.Assignment;
import com.sanzong.obe.entity.CurriculumObj;
import com.sanzong.obe.entity.model.AssignmentToCurriculumObjModel;
import com.sanzong.obe.mapper.AssignmentMapper;
import com.sanzong.obe.mapper.CurriculumObjMapper;
import com.sanzong.obe.service.ICurriculumObjService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class CurriculumObjServiceImpl extends ServiceImpl<CurriculumObjMapper, CurriculumObj> implements ICurriculumObjService {
    @Autowired
    private CurriculumObjMapper curriculumObjMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;


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
}
