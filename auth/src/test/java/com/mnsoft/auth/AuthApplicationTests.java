package com.mnsoft.auth;

import com.mnsoft.common.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AuthApplicationTests {

    @Test
    public void insertTestData() {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into `user`(username,`password`,`status`) values");
        int count = 10000;
        for (int i = 1; i <= count; i++) {
            int len = RandomUtils.nextInt(20);
            if (len < 4) {
                len = 6;
            }
            String uuid = RandomStringUtils.randomAlphabetic(len);
            sb.append("('" + uuid + "','" + uuid + "'," + len + ")");
            sb.append((i == count) ? ";" : ",");
        }
        log.error("*******Inset  SQL*************************SQL******************");
        log.error(System.getProperty("line.separator"));
        log.error(sb.toString());
        log.error(System.getProperty("line.separator"));
        log.error("*******Inset  SQL*************************SQL******************");
    }

    @Test
    public void updateTestData() {
        StringBuilder sb = new StringBuilder();
        sb.append("truncate user_tmp;");
        sb.append("insert into `user_tmp`(user_id,`status`) values");
        int count = 50000;
        for (int i = 1; i <= count; i++) {
            int len = RandomUtils.nextInt(20);
            sb.append("(" + i + "," + len + ")");
            sb.append((i == count) ? ";" : ",");
        }
        log.error("*******Inset  SQL*************************SQL******************");
        log.error(System.getProperty("line.separator"));
        log.error(sb.toString());
        log.error(System.getProperty("line.separator"));
        log.error("*******Inset  SQL*************************SQL******************");
    }

    @Test
    public void updateTestData2() {
        StringBuilder sb = new StringBuilder();
        int count = 10000;
        for (int i = 1; i <= count; i++) {
            int len = RandomUtils.nextInt(20);
            sb.append("update user set `status`="+len+" where user_id ="+i+";");
        }
        log.error("*******Inset  SQL*************************SQL******************");
        log.error(System.getProperty("line.separator"));
        log.error(sb.toString());
        log.error(System.getProperty("line.separator"));
        log.error("*******Inset  SQL*************************SQL******************");
    }


}
