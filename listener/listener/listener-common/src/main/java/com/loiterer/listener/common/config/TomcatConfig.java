package com.loiterer.listener.common.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 项目发布的时候启动这个配置, 将http转为https
 * SpringBoot2.x配置HTTPS,并实现HTTP访问自动转向HTTPS
 * @author XieZhiJie
 * @date 2020/11/11 13:03
 */
@Profile("dev")
@Configuration
public class TomcatConfig {

    @Value("${http.port}")
    private int httpPort;

    @Value("${server.port}")
    private int serverPort;

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        // 监听Http的端口
        connector.setPort(httpPort);
        connector.setSecure(false);
        // 监听Http端口后转向Https端口
        connector.setRedirectPort(serverPort);
        return connector;
    }

}
