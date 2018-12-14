package com.mnsoft.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.oauth.client.web.BaseController;
import com.mnsoft.user.mapper.UserMapper;
import com.mnsoft.user.model.User;
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
     * 测试高可用
     * @param request
     * @return
     */
    @GetMapping("/url")
    public String getHost(HttpServletRequest request) {

        Config config = ConfigService.getAppConfig(); //config instance is singleton for each namespace and is never null
        String someKey = "timeout";
        String someDefaultValue = "100";
        String value = config.getProperty(someKey, someDefaultValue);

        String loginUserId = getUserId();

        return "Host:" + request.getRemoteHost()+ System.getProperty("line.separator", "\n")
                + "  Port:" + request.getServerPort() + System.getProperty("line.separator", "\n")
                + " Path:" + request.getRequestURI() + System.getProperty("line.separator", "\n")
                + " Timeout: " + value + System.getProperty("line.separator", "\n")
                + " Login UserId: " + loginUserId;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        User user = mapper.selectById(id);
        if (user == null) {
            throw new BusinessException(110001,"用户不存在");
            //return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<User> getByAccount(@RequestParam String username,@RequestParam String password) {
        User user = mapper.selectOne(new QueryWrapper<User>().lambda()
                .eq(User::getUsername, username)
                .eq(User::getPassword,password)
        );

        if (user == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Integer> insert(@RequestBody User model) {
        int userId= mapper.insert(model);
        return ResponseEntity.ok(userId);
    }
}