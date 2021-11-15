package com.github.hiling.item.controller;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.DiscoveryManager;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @GetMapping("users")
    public ResponseEntity<List<UserBean>> getUsers(HttpServletRequest request) {
        List<UserBean> users = new ArrayList<UserBean>();
        for (int i = 0; i < 10; i++) {
            users.add(new UserBean("name1_" + String.valueOf(i), i));
        }
        ResponseEntity<List<UserBean>> responseEntity=new ResponseEntity<>(users,HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("oom/{id}")
    public String testOOM(@PathVariable Integer id) {

        List<UserBean> users = new ArrayList<UserBean>();

        for (int i = 0; i < id; i++) {
            users.add(new UserBean("name_" + String.valueOf(i), i));
        }
        return "OK";
    }

    @Data
    public class UserBean {
        String name;
        int age;

        public UserBean(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return createTime;
    }

}
