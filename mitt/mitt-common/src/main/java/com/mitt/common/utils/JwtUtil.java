package com.mitt.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {
    /**
     * 过期时间1天
     */
    private static final long DEFAULT_EXPIRE_TIME = 24 * 3600 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "DD5654D654DSD5S1D65S4D65S1D";


    /**
     * 签名算法
     */
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    /**
     * 生成签名15分钟后过期
     *
     * @param userName 用户名
     * @param userId   用户ID
     * @return 加密的token
     */
    public static String signByUser(String userName, String userId) {
        return signByUser(userName, userId, DEFAULT_EXPIRE_TIME);
    }

    public static String signByUser(String userName, String userId, long expireTime) {
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + expireTime);
            //私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "hs256");
            //附带userName userId信息，生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userName", userName)
                    .withClaim("userId", userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String signByMap(Map<String, Object> claims) {
        return signByMap("mitt", DEFAULT_EXPIRE_TIME, claims);
    }

    public static String signByMap(String iss, long ttlMillis, Map<String, Object> claims) {
        if (claims == null) {
            claims = new HashMap<>();
        }
        // 签发时间（iat）：荷载部分的标准字段之一
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                // 荷载部分的非标准字段/附加字段，一般写在标准的字段之前。
                .setClaims(claims)
                // JWT ID（jti）：荷载部分的标准字段之一，JWT 的唯一性标识，虽不强求，但尽量确保其唯一性。
                .setId(UUID.randomUUID().toString())
                // 签发时间（iat）：荷载部分的标准字段之一，代表这个 JWT 的生成时间。
                .setIssuedAt(now)
                // 签发人（iss）：荷载部分的标准字段之一，代表这个 JWT 的所有者。通常是 username、userid 这样具有用户代表性的内容。
                .setSubject(iss)
                // 设置生成签名的算法和秘钥
                .signWith(SIGNATURE_ALGORITHM, TOKEN_SECRET);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            // 过期时间（exp）：荷载部分的标准字段之一，代表这个 JWT 的有效期。
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verifyByUser(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT JWT = verifier.verify(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获取token中的信息无需secret解密也能获取
     *
     * @param token 密钥
     * @return token中包含的用户名
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException ex) {
            return null;
        }
    }

    /**
     * 获取token中的信息无需secret解密也能获取
     *
     * @param token 密钥
     * @return token中包含的用户ID
     */
    public static String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException ex) {
            return null;
        }
    }


    public static boolean VerifyByMapToken(String jwtToken) {
        Algorithm algorithm = null;
        switch (SIGNATURE_ALGORITHM) {
            case HS256:
                algorithm = Algorithm.HMAC256(Base64.decodeBase64(TOKEN_SECRET));
                break;
            default:
                throw new RuntimeException("不支持该算法");
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            verifier.verify(jwtToken);  // 校验不通过会抛出异常
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 解析JWTToken
     *
     * @param jwtToken jwt令牌
     * @return {@link Claims}
     */
    public static Claims parserByMap(String jwtToken) {
        // 得到 DefaultJwtParser
        return Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(TOKEN_SECRET)
                // 设置需要解析的 jwt
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public static String getUserIdByMap(String jwtToken) {
        Map map = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(TOKEN_SECRET)
                // 设置需要解析的 jwt
                .parseClaimsJws(jwtToken)
                .getBody();
        return (String) map.get("userId");
    }


    public static void main(String[] args) {
        String admin = signByUser("admin", "123456");
        System.out.println(admin);
    }
}