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
        return getUserId() != null;
    }

    public String getUserId() {
        return getByClaimsKey(JwtUtils.USER_ID_KEY);
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
            userInfo.setUserId(claims.get(JwtUtils.USER_ID_KEY).toString());
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
            return claims.get(key).toString();
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
