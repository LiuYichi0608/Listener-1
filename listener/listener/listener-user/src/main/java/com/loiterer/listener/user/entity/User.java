package com.loiterer.listener.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author XieZhiJie
 * @date 2020/10/24 21:19
 */
@TableName("t_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对应t_user表中的id字段
     */
    private Integer id;

    /**
     * 对应t_user表中的name字段
     */
    private String name;

    /**
     * 对应t_user表中的age字段
     */
    private Integer age;

}
