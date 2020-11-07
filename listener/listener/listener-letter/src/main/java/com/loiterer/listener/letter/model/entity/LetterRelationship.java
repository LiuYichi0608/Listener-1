package com.loiterer.listener.letter.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 对应数据库表t_letter_relationship
 * 信件关系
 * </p>
 *
 * @author xzj
 * @since 2020-11-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_letter_relationship")
public class LetterRelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 写信人的id
     */
    private Integer writerId;

    /**
     * 写信人的昵称
     */
    private String writerNickName;

    /**
     * 收件人的id
     */
    private Integer recipientId;

    /**
     * 收信人的昵称
     */
    private String recipientNickName;

    /**
     * 信件id
     */
    private Integer letterId;

    /**
     * 收信人读信状态, 0代表未读, 1代表以读
     */
    private Integer isRead;

    /**
     * 收信人收到信件的时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;


}
