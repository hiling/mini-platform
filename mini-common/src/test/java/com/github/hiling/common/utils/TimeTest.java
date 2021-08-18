package com.github.hiling.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@Slf4j
@RunWith(SpringRunner.class)
public class TimeTest {

    @Test
    public void contextLoads() {
        log.info("月同比:{}", LocalDate.now().minusYears(1).minusMonths(1));
        //log.info("HostName:{}", AddressUtils.getHostName());
    }
}
