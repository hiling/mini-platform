package com.github.hiling.item.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/20/2018.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    /**
     * 测试高可用
     *
     * @param request
     * @return
     */
    @GetMapping("url")
    public String get(HttpServletRequest request) {
        //config instance is singleton for each namespace and is never null
        Config config = ConfigService.getAppConfig();
        String someKey = "timeout";
        String someDefaultValue = "100";
        String value = config.getProperty(someKey, someDefaultValue);

        return ">>>>>" + "Host:" + request.getRemoteHost() + "  Port: 【" + request.getServerPort()
                + "】 Path:" + request.getRequestURI()
                + " Timeout: " + value;
    }

    @GetMapping("oom/{id}")
    public String testOOM(@PathVariable Integer id) {
        List<UserBean> users = new ArrayList<UserBean>();

        for (int i = 0; i < id; i++) {
            users.add(new UserBean("name_" + String.valueOf(i), i));
        }
        return "OK";
    }

    public class UserBean {
        String name;
        int age;

        public UserBean(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
