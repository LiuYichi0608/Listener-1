package com.loiterer.listener.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息类
 *
 * @author cmt
 * @date 2020/10/26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户头像图片的 URL
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
}
