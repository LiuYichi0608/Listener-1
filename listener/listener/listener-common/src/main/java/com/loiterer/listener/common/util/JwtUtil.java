package com.loiterer.listener.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.loiterer.listener.common.exception.ListenerException;
import com.loiterer.listener.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * jwt 工具类
 *
 * @author cmt
 * @date 2020/10/21
 */
@Slf4j
@Component
public class JwtUtil {

    /**
     * token 私钥
     */
    @Value("${jwt.token.secret}")
    private String secret;

    private static DecodedJWT jwt;

    /**
     * 生成签名
     *
     * @return 返回 token
     */
    public String getToken(String openid) {
        // 设置 token 过期时间为7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        return JWT.create()
                // 声明 openid
                .withClaim("openid", openid)
                // 签发时间
                .withIssuedAt(new Date())
                // 过期时间
                .withExpiresAt(calendar.getTime())
                // 生成签名
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证token
     *
     * @param token token
     * @return
     */
    public void verify(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        jwt = verifier.verify(token);
    }

    /**
     * 获取 jwt 中的 openid
     *
     * @return 返回openid
     */
    public String getOpenid() {
        // 防止jwtUtil从token中获取openid失败
        try {
            return jwt.getClaims()
                    .get("openid")
                    .asString();
        } catch (NullPointerException e) {
            log.error(e.getMessage() + "||" + "token获取用户信息失败!");
            throw new ListenerException(ResultCodeEnum.FAIL.getCode(), "token获取用户信息失败!");
        }
    }
}
