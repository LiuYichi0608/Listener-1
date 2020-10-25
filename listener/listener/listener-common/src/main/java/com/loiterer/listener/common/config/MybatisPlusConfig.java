package com.loiterer.listener.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置类
 * @author XieZhiJie
 * @date 2020/10/25 16:52
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 配置分页插件
     * @return 返回分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
