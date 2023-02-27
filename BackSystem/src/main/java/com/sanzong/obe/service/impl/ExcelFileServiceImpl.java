package com.sanzong.obe.service.impl;

import com.alibaba.excel.EasyExcel;
import com.sanzong.obe.listener.NoModelDataReadListener;
import com.sanzong.obe.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author SANZONG
 */
@Slf4j
@Service
public class ExcelFileServiceImpl implements IFileService {
    /**
     * 不创建接收对象读取文件
     */
    @Override
    public void NoModelDataRead(File file) {
        EasyExcel.read(file,  new NoModelDataReadListener()).sheet().doRead();
    }

}
