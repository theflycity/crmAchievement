package com.example.crmachievement.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtConfig {

    private static String SECRET;
    private static long EXPIRATION_TIME;
    private static String ISSUER;
    private static String AUDIENCE;
    private static SecretKey SECRET_KEY;
    // 配置常量（建议从配置文件中读取）
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    } // 至少32字符
    @Value("${jwt.expiration:864000}")
    public void setExpirationTime(long expiration) {
        EXPIRATION_TIME = expiration * 1000L; // 转为毫秒
    } // 10天（单位：毫秒）
    @Value("${jwt.issuer:YourApp}")
    public void setIssuer(String issuer) {
        ISSUER = issuer;
    }

    @Value("${jwt.audience:Client}")
    public void setAudience(String audience) {
        AUDIENCE = audience;
    }        // 接收方


    @PostConstruct
    public void init() {
        if (SECRET == null || SECRET.trim().isEmpty()) {
            throw new IllegalStateException("jwt.secret未配置");
        }
        if (SECRET.length() < 32) {
            throw new IllegalArgumentException("jwt.secret必须至少32字符");
        }
        this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 创建JWT令牌
     * @param subject 用户标识（如username）
     * @param claims 自定义声明（注意不要存放敏感信息）
     */
    public static String createToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)                          // 自定义声明
                .setSubject(subject)                        // 主题（用户标识）
                .setIssuer(ISSUER)                          // 签发者
                .setAudience(AUDIENCE)                      // 接收方
                .setIssuedAt(new Date())                    // 签发时间
                .setExpiration(new Date(
                        System.currentTimeMillis() + EXPIRATION_TIME
                ))                                          // 过期时间
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 签名算法+密钥
                .compressWith(CompressionCodecs.DEFLATE)    // 压缩载荷
                .compact();
    }

    /**
     * 验证并解析JWT令牌
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)                  // 验证签名所需的密钥
                    .requireIssuer(ISSUER)                      // 验证签发者
                    .requireAudience(AUDIENCE)                  // 验证接收方
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

