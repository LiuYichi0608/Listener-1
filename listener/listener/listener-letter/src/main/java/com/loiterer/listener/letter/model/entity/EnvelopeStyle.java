package com.loiterer.listener.letter.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_envelope_style")
public class EnvelopeStyle implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 信件样式id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 信封url
     */
    private String urlEnvelope;

    /**
     * 信纸url
     */
    private String urlPaper;


}
