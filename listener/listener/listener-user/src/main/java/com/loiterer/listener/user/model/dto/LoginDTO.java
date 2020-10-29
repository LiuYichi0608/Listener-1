package com.loiterer.listener.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录时的 DTO
 * @author cmt
 * @date 2020/10/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    /**
     * 微信用户唯一标识
     */
    private String openid;
    /**
     * token
     */
    private String token;
}
