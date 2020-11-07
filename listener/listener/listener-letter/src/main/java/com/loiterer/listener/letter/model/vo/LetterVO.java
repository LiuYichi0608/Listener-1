package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于返回给前端信件, 也用于前端发送需要保存的信件到后端的信息的vo类
 * @author XieZhiJie
 * @date 2020/11/06 13:55
 */
@Data
public class LetterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 信的id
     */
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
     * 信件的样式id
     */
    private Integer envelopeId;

    /**
     * 当前信件的状态, 0代表草稿, 1代表已写好的信
     */
    private Integer status;

}
