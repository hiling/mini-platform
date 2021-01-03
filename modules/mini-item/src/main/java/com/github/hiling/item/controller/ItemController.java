package com.github.hiling.item.controller;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${aaa:default_aaa}")
    private String getAaa;

    @Value("${common:default}")
    private String getCommon;

    @GetMapping("config")
    public String getConfig() {
        return getAaa + " - "+ getCommon;
    }

    /**
     * 测试高可用
     *
     * @param request
     * @return
     */
    @GetMapping("url")
    public String get(HttpServletRequest request) {


        return ">>>>>" + "Host:" + request.getRemoteHost() + "  Port: 【" + request.getServerPort()
                + "】 Path:" + request.getRequestURI();
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
