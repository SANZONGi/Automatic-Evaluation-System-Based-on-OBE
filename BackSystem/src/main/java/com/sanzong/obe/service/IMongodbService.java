package com.sanzong.obe.service;

import com.sanzong.obe.entity.StudentModel;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @author SANZONG
 */
public interface IMongodbService {
    /**
     * 返回学生信息
     * @param name 学生姓名
     * @return 学生信息
     */
    List<StudentModel> getStudentModel(String name);
    List<StudentModel> getStudentModel(Integer id);

    void delStudentModel(String name);

    StudentModel updateStudentModel(String name, boolean isNew);

    StudentModel updateStudentModelByColum(Integer id ,String name, boolean isNew, boolean upsert, Update update);

    boolean insertStudentModel(List<StudentModel> studentModels);
}
