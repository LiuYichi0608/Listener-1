package com.loiterer.listener.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * jwt 工具类
 *
 * @author cmt
 * @date 2020/10/21
 */
@Component
public class JwtUtil {

    /**
     * token 私钥
     */
    @Value("${jwt.token.secret}")
    private String secret;

    /**
     * todo: 设置 jwt 的过期时间
     * 生成签名
     *
     * @return
     */
    public String getToken() {
        // 生成签名
        return JWT.create().sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public void verify(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
    }
}
