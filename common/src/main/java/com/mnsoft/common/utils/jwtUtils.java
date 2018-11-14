package com.mnsoft.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 10/7/2018.
 */
@Slf4j
public class jwtUtils {

    /**
     * 该方法使用HS256算法和Secret生成signKey
     *
     * @return
     */
    private static Key getKeyInstance() {
        //We will sign our jwtUtils with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("Trnj5MFuNPbAq2V0gbHsv9qMENRT12EI");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

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
     *使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     * @param issuer JWT的签发者 (Auth Server)
     * @param subject JWT面向的用户 (User)
     * @param audience 接受JWT的一方 （Client）
     * @param expiration  过期时间
     * @param issuedAt  签发时间
     * @return
     */
    public static String createJavaWebToken(String issuer, String subject, String audience, LocalDateTime expiration, LocalDateTime issuedAt) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setAudience(audience)
                .setExpiration(DateTimeUtils.localDateTimeToDate(expiration))
                .setIssuedAt(DateTimeUtils.localDateTimeToDate(issuedAt))
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
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
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.error("json web token verify failed. error message:" + e.getMessage());
            return null;
        }
    }
}
