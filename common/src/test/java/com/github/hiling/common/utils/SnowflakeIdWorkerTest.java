package com.github.hiling.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hiling
 * @date 2019/7/13 9:49
 */
@RunWith(SpringRunner.class)
public class SnowflakeIdWorkerTest {
    @Test
    public void contextLoads() {
        SnowflakeIdWorker snowflakeIdWorker =new SnowflakeIdWorker(0,0);
        for (int i=0;i<100;i++){
            System.out.println(snowflakeIdWorker.nextId());
        }
    }
}