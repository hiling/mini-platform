package com.mnsoft.oauth.modules.client.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Client {

    //@NotEmpty(message = "客户编号不能为空。")
    @Size(min = 4, max = 16, message = "客户编号长度必须为3至16位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "客户编号只能为数字和字母的组合。")
    private String clientId;

    @NotEmpty(message = "客户名称不能为空。")
    @Size(min = 4, max = 16, message = "客户名称的长度需要在4至16位。")
    private String clientName;

    //@NotEmpty(message = "密钥不能为空")
    private String clientSecret;

    private String ipWhitelist;
    private String redirectUri;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8", locale = "zh")
    private LocalDateTime createTime;

}
