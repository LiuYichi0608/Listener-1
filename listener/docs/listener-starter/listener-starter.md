该模块是启动模块，此模块的作用是放置配置文件。



### 配置文件内容介绍



#### 详情

只有一个启动类。

```java
package com.loiterer.listener;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类, 建议所有Mapper接口都加@Mapper注解, test的时候可以用
 * MapperScan扫描mapper包
 * @author XieZhiJie
 * @date 2020/10/24 20:41
 */
@MapperScan({
        "com.loiterer.listener.common.mapper",
        "com.loiterer.listener.user.mapper",
        "com.loiterer.listener.letter.mapper"
})
@SpringBootApplication
public class ListenerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ListenerApplication.class, args);
    }
}

```



