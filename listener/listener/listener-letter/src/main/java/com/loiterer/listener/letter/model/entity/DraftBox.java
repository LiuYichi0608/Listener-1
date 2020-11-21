package com.loiterer.listener.letter.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xzj
 * @since 2020-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_draft_box")
public class DraftBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 写信人id
     */
    private Integer writerId;

    /**
     * 写信人笔名
     */
    private String writerNickName;

    /**
     * 收件人id
     */
    private Integer recipientId;

    /**
     * 收件人笔名
     */
    private String recipientNickName;

    /**
     * 信件标题
     */
    private String title;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 信件创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 信件最近一次修改的时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    /**
     * 信封样式id
     */
    private Integer envelopeId;

    /**
     * 0表示未读，1表示已读
     * 插入的时候默认设置为0(未读)
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer isRead;

    /**
     * 0代表不是回信, 1代表是回信
     */
    private Integer isReply;

    /**
     * 要回复的那封信的id
     */
    private Integer replyId;

}
