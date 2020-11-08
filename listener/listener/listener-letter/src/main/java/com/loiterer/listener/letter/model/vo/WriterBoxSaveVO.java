package com.loiterer.listener.letter.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端发送保存信件信息时传送的数据
 * @author XieZhiJie
 * @date 2020/11/08 23:47
 */
@Data
public class WriterBoxSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 信件标题
     */
    private String title;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 信封样式id
     */
    private Integer envelopeId;

    /**
     * 是否是回信, 不是回信为0, 否则为1
     */
    private Integer isReply;

}
