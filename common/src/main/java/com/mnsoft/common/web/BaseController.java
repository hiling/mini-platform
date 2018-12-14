package com.mnsoft.common.web;

import com.mnsoft.common.utils.JwtUtils;
import com.mnsoft.common.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/17/2018.
 */
@Slf4j
public class BaseController {
    @Autowired
    private HttpServletRequest request;

    public Boolean isLogin() {
        return getUserId() != null;
    }

    public String getUserId() {
        return getByClaimsKey(JwtUtils.USER_ID_KEY);
    }

    public String getClientId() {
        return getByClaimsKey(JwtUtils.CLIENT_ID_KEY);
    }

    public List<String> getScopeList() {
        String scope = getByClaimsKey(JwtUtils.SCOPE_ID_KEY);
        return StringUtils.stringToList(scope);
    }

    public UserInfo getUserInfo() {
        String jwtToken = request.getHeader("jwtToken");
        Map<String, Object> claims = JwtUtils.parserJavaWebToken(jwtToken);
        if (claims != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(claims.get(JwtUtils.USER_ID_KEY).toString());
            userInfo.setClientId(claims.get(JwtUtils.CLIENT_ID_KEY).toString());
            userInfo.setScopeList(StringUtils.stringToList(claims.get(JwtUtils.SCOPE_ID_KEY).toString()));
            return userInfo;
        }
        return null;
    }

    private String getByClaimsKey(String key) {
        String jwtToken = request.getHeader("jwtToken");
        Map<String, Object> claims = JwtUtils.parserJavaWebToken(jwtToken);
        if (claims != null) {
            return claims.get(key).toString();
        }
        return null;
    }

    @Setter
    @Getter
    public class UserInfo {
        String userId;
        String clientId;
        List<String> scopeList;
    }
}