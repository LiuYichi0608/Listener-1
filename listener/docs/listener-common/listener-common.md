# 模块中的类



## 一、result包



### 1、ResultEntity



#### 所在包

com.loiterer.listener.common.result。



#### 用法

返回给前端统一数据格式的类。



#### 属性

+ private Integer **status**：返回给前端的状态码。

+ private String **message**：返回给前端的信息。

+ private Map<String, Object> **data** = new HashMap<>(16)：返回给前端的具体数据。

  

#### 构造方法

+ private ResultEntity()：私有化该方法，使得外界无法直接创建该类的对象。

  



#### 其他方法

+ public static ResultEntity success()：
  + 当方法调用成功的时候设置值并返回一个ResultEntity对象。
  + @return：返回一个ResultEntity对象方便链式设值。

+ public static ResultEntity fail()：
  + 当方法调用失败的时候设值值并返回一个ResultEntity对象。
  + @return：返回一个ResultEntity对象方便链式设值。

+ public ResultEntity status(Integer status)：
  + 设置状态码。
  + @param status：要设置的状态码的值。
  + @return：返回当前对象方便链式设值。

+ public ResultEntity message(String message)：
  + 设置信息。
  + @param message：要设置的信息的值。
  + @return：返回当前对象方便链式设值。

+ public ResultEntity data(String key, Object value)：
  + 将数据放入data中。
  + @param key：数据的key。
  + @param value：数据的值。
  + @return：返回当前对象方便链式设值。

+ public ResultEntity data(Map<String, Object> map)：
  + 将一个map格式的数据设置成为data, map中可存放多个键值对。
  + @param map：要设置成为data的map。
  + @return：返回当前对象方便链式设值。



#### 该类用法

##### 代码示例1

具体细节可参考模块listener-user(里面的代码暂时用于测试和示例)。

```java
    /**
     * 用于测试能够获得mysql和redis数据库的数据并返回
     * Get和Post提交方式
     * @return 返回数据
     */
    @RequestMapping("/findAll")
    public ResultEntity findAll() {
        return ResultEntity.success()
                .data("mysqlData", userService.findAll())
                .data("redisData", redisTemplate.opsForValue().get("a"));
    }
```

解释：使用ResultEntity.success()后会返回一个ResultEntity对象，且此对象的message设置为"success"，然后可以通过这个返回的对象继续设值，.data(key, value)把需要返回给前端的值放入到该对象中，最后通过return把该对象返回给前端。注意，这里的Controller类已经加了@RestController注解，因此返回给前端的是一个json数据。

以下是返回给前端后拿到的json数据(以下的数据都是放在redis和mysql里的测试数据)：

```json
{
    "code": 0,
    "message": "success",
    "data": {
        "redisData": "1",
        "mysqlData": [
            {
                "id": 1,
                "name": "张三",
                "age": 21
            },
            {
                "id": 2,
                "name": "李四",
                "age": 22
            },
            {
                "id": 3,
                "name": "王五",
                "age": 23
            }
        ]
    }
}
```



目前就这些。



### 2、ResultCodeEnum



#### 所在包

com.loiterer.listener.common



#### 用法

用于定义返回给前端的**自定义状态码**。



#### 属性

+ Integer **code**：状态码的值。



#### 枚举类对象

+ SUCCESS：成功状态码。
  + code = 20000
+ FAIL：失败状态码。
  + code = 20001
+ NOT_FOUND：找不到处理该请求的handler(controller)的异常
  + code = 404



#### 该枚举类用法



##### 代码示例1

```java
    public static ResultEntity success() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setStatus(ResultCode.SUCCESS.getCode());
        resultEntity.setMessage("success");
        return resultEntity;
    }
```

解释：这一段代码是ResultEntity里的代码，可以看到第三行用到了ResultCodeEnum的属性。





## 二、exception包



### 1、ListenerException



#### 所在包

com.loiterer.listener.common.exception。



#### 用法

这是一个自定义异常

当出现错误的时候，可以抛出这个错误



#### 继承

java.lang.RuntimeException



#### 属性

+ private Integer code：状态码
+ private String message：异常信息



#### 该类用法



##### 代码示例1

```java
    /**
     * 抛出错误的一个测试接口
     * @return 返回一个错误
     */
    @RequestMapping("/error")
    public ResultEntity error() {
        throw new ListenerException(ResultCode.FAIL.getCode(), "自定义异常!");
    }
```

这是一个测试接口，抛出一个自定义异常。





## 三、config包



#### 1、MybatisConfig



##### 所在包

com.loiterer.listener.common.config



##### 用法

是一个spring的配置类，用于配置Mybatis配置，目前只配置了一个分页插件。



##### 方法

+ public PaginationInterceptor paginationInterceptor()
  + 配置分页插件
  + @return：返回分页插件



##### 该类用法



###### 代码示例1

```java
    @Override
    public Page<User> pageList(Long page, Long limit) {
        Page<User> userPage = new Page<>(page, limit);
        // 第二个参数传入null代表没有查询条件
        return userMapper.selectPage(userPage, null);
    }
```

当配置了分页插件以后，使用Page可以设置分页，通过传入page(当前页)，limit(每页记录数)后，可以得到除了查询记录外的其他数据。

代码中的userMapper要继承com.baomidou.mybatisplus.core.mapper.BaseMapper。



## 四、handler包



### 1、GlobalExceptionHandler



#### 所在包

com.loiterer.listener.common.handler



#### 用法

这个类配置了处理三种异常的方法，Exception，org.springframework.web.servlet.NoHandlerFoundException(404异常)和com.loiterer.listener.common.exception.ListenerException(自定义异常)。当程序抛出404异常和自定义异常的时候，会被后两个异常处理方法捕获，其它异常被第一个方法捕获，目的是返回给前端统一的ResultEntity的json数据。



#### 方法

+ public ResultEntity listenerException(ListenerException e)
  + 自定义异常处理
  + @param e：自定义异常
  + @return：返回统一的自定义异常的信息

+ public ResultEntity notFountException(NoHandlerFoundException e)
  + 404异常处理
  + @param e：找不到请求的handler异常
  + @return：返回统一的404异常的信息

+ public ResultEntity error(Exception e)
  + 处理所有异常的方法
  + @param e：Exception异常
  + @return：返回统一的异常信息



#### 该类用法



使用一些不存在的请求，会返回404的json数据，不是由返回springboot的，而是返回自定义的。

其它异常同理。





# application.yml

本来放在listener-starter模块，但因为测试的时候需要springboot环境，于是将该配置文件放在listener-common中。

```yaml
server:
  # 端口号
  port: 8080
  servlet:
    # 8080后的路径名 http://xxx:8080/listener
    context-path: /listener

# spring config
spring:
  application:
    name: server-listener
  datasource:
    name: listener-db
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://47.112.101.204:3306/test?serverTimezone=CTT&useUnicode=true&characterEncoding=UTF8
    username: root
    password: ENC(r27PCZ7dxDJzgSk/zamd2KlJ5RGOxgQd8HotERRXnBA=)
  redis:
    host: 47.112.101.204
    port: 6379
    password: ENC(N39ZWiVrwXEfPydon9zJV25JDSa4DZDSkDtBZ7xZ0x4=)
    jedis:
      pool:
        max-active: 20
  # 配置json的全局时间格式
  jackson:
    # 格式
    date-format: yyyy-MM-dd HH:mm:ss
    # 东八区
    time-zone: GMT+8
  # mvc相关
  mvc:
    # 出现找不到处理器错误时候, 直接抛出异常
    throw-exception-if-no-handler-found: true
  resources:
    # 不要为工程中的资源文件建立映射(可能会出现如果使用静态资源无法使用的结果, 比如swagger, 需要手动配置)
    add-mappings: false

# mybatis config
mybatis-plus:
  # 映射mapper.xml文件的路径
  mapper-locations: classpath:/mybatis/mapper/*.xml
  configuration:
    # 开启驼峰命名法
    map-underscore-to-camel-case: true
    # 开启以下配置, 打印mapper的日志, 查看sql语句
    # log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

# 对配置文件中的信息进行加密操作
# jasypt 密码加密配置
jasypt:
  encryptor:
    # 加密盐值
    password: ${JASYPT_ENCRYPTOR_PASSWORD}
    # 加密算法设置 3.0.0 以后
    algorithm: PBEWithMD5AndDES
    iv-generator-classname: org.jasypt.iv.NoIvGenerator
```



