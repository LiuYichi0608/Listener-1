package com.loiterer.listener.letter.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 对应数据库表t_letter
 * 信件信息
 * </p>
 *
 * @author xzj
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_letter")
public class Letter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 信件的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 信的标题
     */
    private String title;

    /**
     * 信件的内容
     */
    private String content;

    /**
     * 写这封信的用户的id
     */
    private Integer writerId;

    /**
     * 写信人的昵称
     */
    private String writerNickName;

    /**
     * 信件的样式id
     */
    private Integer envelopeId;

    /**
     * 当前信件的状态, 0代表草稿, 1代表已写好的信 
     */
    private Integer status;

    /**
     * 信件的创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;


}
