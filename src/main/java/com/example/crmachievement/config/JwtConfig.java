package com.example.crmachievement.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtConfig {

    private String secret;
    private long expiration_time;
    private String issuer;
    private String audience;
    private SecretKey secret_key;
    // 建议从配置文件中读取
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalStateException("jwt.secret未配置");
        }
        if (secret.length() < 32) {
            throw new IllegalArgumentException("jwt.secret必须至少32字符");
        }
        this.secret = secret;
    } // 至少32字符

    @Value("${jwt.expiration:864000}")
    public void setExpirationTime(long expiration) {
        this.expiration_time = expiration * 1000L; // 转为毫秒
    } // 10天（单位：毫秒）

    @Value("${jwt.issuer:YourApp}")
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    @Value("${jwt.audience:Client}")
    public void setAudience(String audience) {
        this.audience = audience;
    } // 接收方

    @PostConstruct
    public void init() {
        if (secret == null || secret.trim().isEmpty()) {
            throw new IllegalStateException("jwt.secret未配置");
        }
        if (secret.length() < 32) {
            throw new IllegalArgumentException("jwt.secret必须至少32字符");
        }
        this.secret_key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 创建JWT令牌
     * @param userId 用户标识
     * @param claims 自定义声明（注意不要存放敏感信息）
     */
    public String createToken(String userId, Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)                          // 自定义声明
                .setSubject(userId)                        // 主题（用户标识）
                .setIssuer(issuer)                          // 签发者
                .setAudience(audience)                      // 接收方
                .setIssuedAt(new Date())                    // 签发时间
                .setExpiration(new Date(
                        System.currentTimeMillis() + expiration_time
                ))                                          // 过期时间
                .signWith(secret_key, SignatureAlgorithm.HS256) // 签名算法+密钥
                .compressWith(CompressionCodecs.DEFLATE)    // 压缩载荷
                .compact();
    }

    /**
     * 验证并解析JWT令牌
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secret_key)                  // 验证签名所需的密钥
                    .requireIssuer(issuer)                      // 验证签发者
                    .requireAudience(audience)                  // 验证接收方
                    .setAllowedClockSkewSeconds(30)             // 允许30秒时钟偏移
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token已过期",e);
        } catch (JwtException e) {
            throw new RuntimeException("Token无效",e);
        }
    }
}

