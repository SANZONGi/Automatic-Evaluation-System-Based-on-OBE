package com.sanzong.obe.service;

import com.sanzong.obe.entity.StudentModel;

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

    void delStudentModel(String name);

    StudentModel updateStudentModel(String name, boolean isNew);

    boolean insertStudentModel(List<StudentModel> studentModels);
}
