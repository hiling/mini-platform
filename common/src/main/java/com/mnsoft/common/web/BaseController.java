package com.mnsoft.common.web;

import com.mnsoft.common.utils.jwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/17/2018.
 */
@Slf4j
public class BaseController {
    @Autowired
    private HttpServletRequest request;

    public String getUserId() {
        String jwtToken = request.getHeader("jwtToken");
        Map<String, Object> claims = jwtUtils.parserJavaWebToken(jwtToken);
        if (claims != null) {
            String userId = claims.get("sub").toString();
            return userId;
        } else {
            return null;
        }
    }
}