package com.github.hiling.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
public class AddressUtilsTest {
    @Test
    public void contextLoads() {
        log.info("HostAddress:{}", AddressUtils.getHostAddress());
        log.info("HostIp:{}", AddressUtils.getHostIp());
        //log.info("HostName:{}", AddressUtils.getHostName());
    }
}
