package com.xinghuo.ams.operateLog.service;

import lombok.extern.slf4j.Slf4j;
import testng.BaseTestNGTest;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class OperateLogServiceImplTest  extends BaseTestNGTest {

    @Resource
    private OperateLogService operateLogService;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);


}
