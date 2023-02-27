package com.sanzong.obe.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sanzong.obe.entity.StudentModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author SANZONG
 */
@Component
@Slf4j
public class NoModelDataReadListener implements ReadListener<Map<Integer, String>> {
    public static NoModelDataReadListener noModelDataReadListener;
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private Map<Integer, String> header = null;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init(){
        noModelDataReadListener = this;
        noModelDataReadListener.mongoTemplate = this.mongoTemplate;
    }
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {

        // 如果想转成成 Map<Integer,String>
        // 方案1： 不要implements ReadListener 而是 extends AnalysisEventListener
        // 方案2： 调用 ConverterUtils.convertToStringMap(headMap, context) 自动会转换
        header = ConverterUtils.convertToStringMap(headMap, context);

        log.info("解析到一条头数据:{}", JSON.toJSONString(header));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        int curId = getId(header.get(0));
        for (Map<Integer,String> item:
             cachedDataList) {
            StudentModel studentModel = new StudentModel();
            studentModel.setName(item.get(0));
            studentModel.setDetail(new JSONArray());
            studentModel.setRealDetail(new JSONObject());
            studentModel.setCurId(String.valueOf(curId));
            for (int i = 1;i < header.size();i++) {
                JSONObject detailItem = new JSONObject();
                detailItem.put("assignment", header.get(i));
                detailItem.put("score", item.get(i));
                studentModel.getDetail().add(detailItem);
            }
            for (int i = 1;i < header.size();i++) {
                studentModel.getRealDetail().put(String.valueOf(getId(header.get(i))), item.get(i));
            }
            noModelDataReadListener.mongoTemplate.insert(studentModel);

            log.info("Mongo 格式 {}", studentModel);
        };
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