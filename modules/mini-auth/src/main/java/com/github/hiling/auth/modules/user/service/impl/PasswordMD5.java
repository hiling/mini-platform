package com.github.hiling.auth.modules.user.service.impl;

import com.github.hiling.common.utils.StringUtils;
import com.github.hiling.auth.modules.user.service.PasswordHash;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/30/2018.
 */
public class PasswordMD5 implements PasswordHash {

    /**
     * 秘密密钥算法
     * 支持类型：MD2、MD5、SHA-1、SHA-224、SHA-256、SHA-384、SHA-512
     * 参考文档：https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#MessageDigest
     */
    @Value("${oauth.user.password.md5.algorithm:MD5}")
    private String algorithm;

    @Override
    public boolean validate(String password, String salt, String hashPassword) {
        try {

            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String md5Password = StringUtils.toEncodedString(bytes, StandardCharsets.UTF_8);
            return StringUtils.equals(hashPassword, md5Password);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}
