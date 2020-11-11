package com.loiterer.listener.letter.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 写信需要保存的信息
 * @author XieZhiJie
 * @date 2020/11/11 20:07
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

}
