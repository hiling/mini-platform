package com.github.hiling.user.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author hiling
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String nickName;

    @TableField(select = false)
    private String password;
}