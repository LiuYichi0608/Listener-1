package com.loiterer.listener.letter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 该启动类的用处是让测试类能够正常运行, 没有其他用处, 需要启动项目到listener-starter模块
 * @author XieZhiJie
 * @date 2020/10/25 20:17
 */
@ComponentScan(basePackages = {"com.loiterer.listener"})
@SpringBootApplication
public class LetterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LetterApplication.class, args);
    }
}
