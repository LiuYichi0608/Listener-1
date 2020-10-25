package com.loiterer.listener.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义异常.
 * 在idea中@Data出现警告的原因是意思是: 注解在实现 ToString EqualsAndHashCode 方法时,
 * 不会考虑父类的属性, 因此用@EqualsAndHashCode的callSuper=false显示表明不需要考虑父类
 * 的属性, 当需要用到父类的属性的时候, 则将false改为true.
 * @author XieZhiJie
 * @date 2020/10/25 13:01
 */
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListenerException extends RuntimeException {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

}
