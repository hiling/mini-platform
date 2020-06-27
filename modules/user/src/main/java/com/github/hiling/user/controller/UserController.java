package com.github.hiling.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.hiling.common.exception.BusinessException;
import com.github.hiling.oauth.client.web.BaseController;
import com.github.hiling.oauth.client.web.UserInfo;
import com.github.hiling.user.mapper.UserMapper;
import com.github.hiling.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserMapper mapper;

    /**
     * @param request
     * @return
     */
    @GetMapping("/demo")
    public String demo(HttpServletRequest request) {

        //Apollo配置中心示例
        //config instance is singleton for each namespace and is never null
        Config config = ConfigService.getAppConfig();
        String timeoutKey = "timeout";
        String timeoutDefaultValue = "100";
        String value = config.getProperty(timeoutKey, timeoutDefaultValue);

        //OAuth Client示例
        Boolean isLogin = isLogin();
        Long loginUserId = getUserId();
        UserInfo userInfo = getUserInfo();

        //高可用测试
        String path = request.getRemoteHost() + ":" + request.getServerPort();

        return " Path:" + path + System.getProperty("line.separator", "\n")
                + " Timeout: " + value + System.getProperty("line.separator", "\n")
                + " is login: " + isLogin + System.getProperty("line.separator", "\n")
                + " userId: " + loginUserId + System.getProperty("line.separator", "\n")
                + " userName: " + (userInfo == null ? "Null" : userInfo.getUserName()) + System.getProperty("line.separator", "\n")
                + " clientId: " + (userInfo == null ? "Null" : userInfo.getClientId()) + System.getProperty("line.separator", "\n")
                ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        User user = mapper.selectById(id);
        if (user == null) {
            throw new BusinessException(110001, "用户不存在");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<User> getByAccount(@RequestParam String username, @RequestParam String password) {
        User user = mapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getUsername, username)
                .eq(User::getPassword, password)
        );

        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Integer> insert(@RequestBody User model) {
        int userId = mapper.insert(model);
        return ResponseEntity.ok(userId);
    }
}