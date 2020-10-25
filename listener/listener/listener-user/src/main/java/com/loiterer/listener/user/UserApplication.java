package com.loiterer.listener.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 该启动类的用处是让测试类能够正常运行, 没有其他用处, 需要启动项目到listener-starter模块
 * @author XieZhiJie
 * @date 2020/10/25 20:15
 */
@ComponentScan(basePackages = {"com.loiterer.listener"})
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
