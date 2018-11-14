package com.mnsoft.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mnsoft.common.exception.BusinessException;
import com.mnsoft.common.web.BaseController;
import com.mnsoft.user.mapper.UserMapper;
import com.mnsoft.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        User user = mapper.selectById(id);
        if (user == null) {
            throw new BusinessException(110001,"用户不存在");
            //return ResponseEntity.notFound().build();
        }
        user.setPassword(getUserId());
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