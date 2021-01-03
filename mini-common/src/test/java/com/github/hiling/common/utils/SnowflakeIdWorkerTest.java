package com.github.hiling.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author hiling
 * @date 2019/7/13 9:49
 */
@Slf4j
@RunWith(SpringRunner.class)
public class SnowflakeIdWorkerTest {
    @Test
    public void contextLoads() {
        List<Long> ids = Collections.synchronizedList(new ArrayList<>());

        long s = System.currentTimeMillis();
        IntStream.range(0, 100000).parallel().forEach(i -> {
            ids.add(SnowflakeIdWorker.getSingleton().nextId());
        });
        log.info("系统花费时间：{}毫秒", System.currentTimeMillis() - s);
        List<Long> filterIds = ids.stream().distinct().collect(Collectors.toList());
        log.info("生产ID数：{}", ids.size());
        log.info("过滤重复ID数：{}", filterIds.size());
        log.info("重复ID数:{}", ids.size() - filterIds.size());
    }
}