package com.github.hiling.oauth.client.web;

import com.github.hiling.oauth.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/14/2018.
 */
public class BaseController {
    @Autowired
    private HttpServletRequest request;

    public Boolean isLogin() {
        return getClientId() != null;
    }

    public Long getUserId() {
        String userId = getByClaimsKey(JwtUtils.USER_ID_KEY);
        return userId == null ? 0 : Long.parseLong(userId);
    }

    public String getUserName() {
        return getByClaimsKey(JwtUtils.USER_NAME_KEY);
    }

    public String getClientId() {
        return getByClaimsKey(JwtUtils.CLIENT_ID_KEY);
    }

    public List<String> getScopeList() {
        String scope = getByClaimsKey(JwtUtils.SCOPE_KEY);
        return stringToList(scope);
    }

    public UserInfo getUserInfo() {
        String jwtToken = request.getHeader("jwtToken");
        Map<String, Object> claims = JwtUtils.parserJavaWebToken(jwtToken);
        if (claims != null) {
            UserInfo userInfo = new UserInfo();
            Object userId = claims.get(JwtUtils.USER_ID_KEY);
            Object userName = claims.get(JwtUtils.USER_NAME_KEY);
            userInfo.setUserId(userId == null ? 0 : Long.parseLong(userId.toString()));
            userInfo.setUserName(userName == null ? "" : userName.toString());
            userInfo.setClientId(claims.get(JwtUtils.CLIENT_ID_KEY).toString());
            userInfo.setScopeList(stringToList(claims.get(JwtUtils.SCOPE_KEY).toString()));
            return userInfo;
        }
        return null;
    }

    private String getByClaimsKey(String key) {
        String jwtToken = request.getHeader("jwtToken");
        Map<String, Object> claims = JwtUtils.parserJavaWebToken(jwtToken);
        if (claims != null) {
            Object value = claims.get(key);
            return value == null ? null : value.toString();
        }
        return null;
    }

    private List<String> stringToList(String string) {

        String[] scopes = org.apache.commons.lang3.StringUtils.split(string, ",");
        if (scopes == null) {
            return null;
        }
        List<String> scopeList = Arrays.asList(scopes);
        return scopeList;
    }
}
