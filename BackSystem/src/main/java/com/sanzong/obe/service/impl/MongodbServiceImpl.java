package com.sanzong.obe.service.impl;

import com.sanzong.obe.entity.StudentModel;
import com.sanzong.obe.service.IMongodbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SANZONG
 */
@Service
public class MongodbServiceImpl implements IMongodbService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<StudentModel> getStudentModel(String name) {
        Query query = new Query(Criteria.where("NAME").is(name));
        return mongoTemplate.find(query, StudentModel.class);
    }

    @Override
    public void delStudentModel(String name) {
        Query query = new Query(Criteria.where("NAME").is(name));
        mongoTemplate.remove(query);
    }

    @Override
    public StudentModel updateStudentModel(String name, boolean isNew) {
        Query query = new Query(Criteria.where("NAME").is(name));
        FindAndModifyOptions options = new FindAndModifyOptions().remove(false).returnNew(isNew).upsert(false);
        return mongoTemplate.findAndModify(query, new Update(), options,StudentModel.class);
    }

    @Override
    public boolean insertStudentModel(List<StudentModel> studentModels) {
        mongoTemplate.insertAll(studentModels);
        return false;
    }
}
