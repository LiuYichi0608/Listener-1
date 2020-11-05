package com.loiterer.listener.letter.entity;

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
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_writer_box")
public class WriterBox implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户的id
     */
      @TableId(value = "uid", type = IdType.INPUT)
    private Integer uid;

    /**
     * 写信人id
     */
    private Integer writerId;

    /**
     * 写信人笔名
     */
    private String writerName;

    /**
     * 收件人id
     */
    private Integer recipientId;

    /**
     * 收件人笔名
     */
    private String recipientName;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 信件创建时间
     * 当插入一条信息的时候, 会被一个处理器(已写好)拦截并设置为当前时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 信封样式id
     */
    private Integer envelopeId;

    /**
     * 0表示未读，1表示已读/写信默认为1，已读
     */
    private Integer isRead;


}
