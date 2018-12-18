package com.github.hiling.oauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/14/2018.
 */
public class JwtUtils {

    private static Logger log = Logger.getLogger("JwtUtils");
    /**
     * 用户编号：sub = Subject, JWT面向的用户
     */
    public static final String USER_ID_KEY = "sub";

    /**
     * 客户端编号：aud = Audience 接受JWT的一方
     */
    public static final String CLIENT_ID_KEY = "aud";

    /**
     * 授权范围，自定义属性
     */
    public static final String SCOPE_KEY = "scp";

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     *
     * @param claims
     * @return
     */
    public static String createJavaWebToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    /**
     * 使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     *
     * @param userId     = sub JWT面向的用户 (User)
     * @param clientId   = aud 接受JWT的一方 （Client）
     * @param expiration = exp  过期时间
     * @param issuedAt   = iat  签发时间
     * @return
     */
    public static String createJavaWebToken(String userId, String clientId, String scope,
                                            Date expiration, Date issuedAt) {

        Claims claims = Jwts.claims();
        claims.put(USER_ID_KEY, userId);
        claims.put(CLIENT_ID_KEY, clientId);
        claims.put(SCOPE_KEY, scope);

        String token = Jwts.builder()
                .setClaims(claims)
                //JWT的签发者
                //.setIssuer("oauth")
                //.setSubject(userId)
                //.setAudience(clientId)
                .setExpiration(expiration)
                .setIssuedAt(issuedAt)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
        return token;
    }

    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     *
     * @param jwt
     * @return
     */
    public static Map<String, Object> parserJavaWebToken(String jwt) {

        if (jwt == null || jwt.isEmpty()) {
            return null;
        }

        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        }

        try {
            Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.warning("json web token verify failed. error message:" + e.getMessage());
            return null;
        }
    }

    /**
     * 该方法使用HS256算法和Secret生成signKey
     *
     * @return
     */
    private static Key getKeyInstance() {
        //We will sign our JwtUtils with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("Trnj5MFuNPbAq2V0gbHsv9qMENRT12EI");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }
}