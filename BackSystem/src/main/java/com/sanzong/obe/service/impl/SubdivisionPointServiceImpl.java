package com.sanzong.obe.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzong.obe.entity.SubdivisionPoint;
import com.sanzong.obe.mapper.SubdivisionPointMapper;
import com.sanzong.obe.service.ISubdivisionPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sanzong
 * @since 2023-01-16
 */
@Service
public class SubdivisionPointServiceImpl extends ServiceImpl<SubdivisionPointMapper, SubdivisionPoint> implements ISubdivisionPointService {

    @Autowired
    private SubdivisionPointMapper subdivisionPointMapper;

    @Override
    public JSONArray getMatrix(String gradReqId) {
        return null;
    }

//    @Override
//    public JSONArray getMatrix(String gradReqId) {
//        // getData
//        List<SubdivisionPoint> subdivisionPoints = subdivisionPointMapper.selectList(new QueryWrapper<SubdivisionPoint>().eq("graduation_requirement_id", gradReqId));
//        List<AssignmentToCurriculumObjModel> curriculumObjWithAssignments = curriculumObjMapper.getCurriculumObjWithAssignment();
//        List<Assignment> assignments = assignmentMapper.selectList(
//                new QueryWrapper<Assignment>().in(
//                        "curriculum_id", curriculumObjs
//                                .stream()
//                                .map(CurriculumObj::getId)
//                                .collect(Collectors.toCollection(ArrayList::new))
//                )
//        );
//        // getConnectSet
//        HashMap<Pair<Integer, Integer>, Integer> weights = new HashMap<>(100);
//        for (AssignmentToCurriculumObjModel item : curriculumObjWithAssignments) {
//            weights.put(new Pair<>(item.getCurriculumObjId(), item.getAssignmentId()), item.getWeight());
//        }
//        JSONArray jsonArray = new JSONArray();
//        for (Assignment assignment : assignments) {
//            JSONArray objArray = new JSONArray();
//            for (CurriculumObj obj : curriculumObjs) {
//                JSONObject jsonObject = new JSONObject();
//                Pair<Integer, Integer> pair = new Pair<>(obj.getId(), assignment.getId());
//                int weight = weights.getOrDefault(pair, 0);
//
//                jsonObject.put("curriculumObj", obj);
//                jsonObject.put("weight", weight);
//                objArray.add(jsonObject);
//            }
//            JSONObject temp = new JSONObject();
//            temp.put("assignment", assignment);
//            temp.put("objArray", objArray);
//            jsonArray.add(temp);
//        }
//        return jsonArray;
//    }
}
