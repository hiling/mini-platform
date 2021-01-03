package com.github.hiling.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GatewayApplicationTests {

    @Test
    public void encodingBCrypt() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //加密时使用
        String hashPassword = passwordEncoder.encode("12345");
        log.info("BCrypt HashPassword: {}", hashPassword);
    }

    @Test
    public void encodingPBKDF2() {
        //加密时使用
        //salt必填，且需要为十六进制字符串
        String salt = DatatypeConverter.printHexBinary("hayden".getBytes());
        String hashPassword = getPBKDF2("12345", salt);
        log.info("PBKDF2 HashPassword: {} , saltSecureRandom: {}", hashPassword, salt);
    }

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

    private String getPBKDF2(String password, String salt) {
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
