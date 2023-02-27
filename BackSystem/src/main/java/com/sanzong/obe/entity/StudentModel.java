package com.sanzong.obe.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author SANZONG
 */
@Data
public class StudentModel {
    @Id
    private String objectId;

    @Field("NAME")
    private String name;

    private String curId;

    private JSONArray detail;

    private JSONObject realDetail;
}
