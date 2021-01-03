package com.github.hiling.auth.modules.user.service.impl;

import com.github.hiling.common.utils.StringUtils;
import com.github.hiling.auth.modules.user.service.PasswordHash;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/30/2018.
 * 帮助文档：
 * https://docs.oracle.com/javase/8/docs/api/javax/crypto/SecretKeyFactory.html
 * http://jszx-jxpt.cuit.edu.cn/JavaAPI/javax/crypto/SecretKeyFactory.html
 */
public class PasswordPBKDF2 implements PasswordHash {

    /**
     * 秘密密钥算法
     * 支持类型：AES、ARCFOUR、DES、DESede、PBEWith<digest>And<encryption>、PBEWith<prf>And<encryption>、PBKDF2With<prf>
     * 参考文档：https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#SecretKeyFactory
     */
    @Value("${oauth.user.password.pbkdf2.algorithm:PBKDF2WithHmacSHA1}")
    String algorithm;

    /**
     * 导出的密钥长度
     */
    @Value("${oauth.user.password.pbkdf2.keyLength:128}")
    int keyLength;

    /**
     * 迭代次数
     */
    @Value("${oauth.user.password.pbkdf2.iterationCount:1024}")
    int iterationCount;

    @Override
    public boolean validate(String password, String salt, String hashPassword) {
        String encodedPassword = getPbkdf2(password, salt);
        return StringUtils.equals(hashPassword, encodedPassword);
    }

    private String getPbkdf2(String password, String salt) {
        try {
            byte[] bytes = DatatypeConverter.parseHexBinary(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), bytes, iterationCount, keyLength);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return "";
        }
    }
}