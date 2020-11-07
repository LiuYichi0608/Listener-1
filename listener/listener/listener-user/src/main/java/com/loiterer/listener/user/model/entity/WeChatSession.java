package com.loiterer.listener.user.model.entity;

import lombok.Data;

/**
 * 微信会话实体类
 * @author cmt
 * @date 2020/10/21
 */
@Data
public class WeChatSession {
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 会话秘钥
     */
    private String session_key;
    /**
     * 错误码
     */
    private Integer errcode;
    /**
     * 错误信息
     */
    private String errmsg;
}
