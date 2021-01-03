package com.github.hiling.auth.controller;

import com.github.hiling.auth.constant.ErrorMessage;
import com.github.hiling.auth.model.AccessToken;
import com.github.hiling.auth.service.AccessTokenService;
import com.github.hiling.common.exception.BusinessException;
import com.github.hiling.common.utils.AddressUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/token")
@Api(value = "Token")
public class TokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("current Time:"+LocalDateTime.now().toString());
    }

    /**
     * 验证 Account Token (introspect)
     *
     * @return 返回jwtToken，如果token不存在或已过期，返回Null
     */
    @GetMapping()
    @ApiOperation(value = "验证 Account Token (introspect)", notes = "返回jwtToken，如果token不存在或已过期，返回Null")
    public ResponseEntity<String> getToken(@RequestParam(name = "access_token") String accessToken) {
        //如果没有找到，返回token过期或token非法，客户端需要通过refreshToken重新来获取accessToken
        String jwtToken = accessTokenService.getJwtToken(accessToken);
        if (StringUtils.isEmpty(jwtToken)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jwtToken);
    }

    //oauth2/introspect
    //oauth2/revoke

    /**
     * 获取 AccessToken,暂不支持scope
     *
     * @param
     * @param grantType
     * @param username 当grant_type=password时，username、password有效
     * @param password
     * @param refreshToken 当grant_type=refresh_token时，该参数有效
     * @return
     */
    @PostMapping()
    @ApiOperation(value = "获取 AccessToken,暂不支持scope")
    public ResponseEntity<AccessToken> postToken(
//            @RequestHeader("client_id") String clientId,
//            @RequestHeader("client_secret") String clientSecret,
            HttpServletRequest request,
            @RequestParam(name = "grant_type") String grantType,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            @RequestParam(name = "refresh_token", defaultValue = "") String refreshToken) {

        // client_id & client_secret可以通过参数传递，也可以通过HTTP Header的Authorization传递
        String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");

        if (StringUtils.isEmpty(clientId)) {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Basic ")) {
                throw new BusinessException(ErrorMessage.TOKEN_AUTHORIZATION_ERROR);
            }

            try {
                String authDecode = new String(Base64Utils.decodeFromString(authorization.substring(6)));
                String[] accounts = authDecode.split(":");
                clientId = accounts[0];
                //Password模式时，clientId必填，clientSecret是可选的。
                if (accounts.length > 1) {
                    clientSecret = accounts[1];
                }
            } catch (Exception e) {
                throw new BusinessException(ErrorMessage.TOKEN_AUTHORIZATION_ERROR);
            }
        }

        String accessIp = AddressUtils.getHttpRequestIp(request);

        AccessToken accessToken = accessTokenService.createAccessToken(accessIp, clientId, clientSecret, grantType, username, password, refreshToken);
        if (accessToken != null) {
            return ResponseEntity.ok(accessToken);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * 吊销 Account Token (revoke)
     *
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<Boolean> revoke(@RequestParam(name = "access_token") String accessToken) {
        //如果没有找到，返回token过期或token非法，客户端需要通过refreshToken重新来获取accessToken
        Boolean result = accessTokenService.deleteToken(accessToken);
        return ResponseEntity.ok(result);
    }
}