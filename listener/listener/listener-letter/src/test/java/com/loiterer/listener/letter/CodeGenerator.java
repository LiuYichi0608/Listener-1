package com.loiterer.listener.letter;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author somebody
 * @since 2018/12/13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CodeGenerator {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        String projectPath = "E:/code/java_code/work/20201024/listener/listener/listener-letter";
        gc.setOutputDir(projectPath + "/src/main/java");

        gc.setAuthor("xzj");
        gc.setOpen(false);  // 生成后是否打开资源管理器
        gc.setFileOverride(false);  // 重新生成时文件是否覆盖

        gc.setServiceName("%sService");	 // 去掉Service接口的首字母I

        // gc.setIdType(IdType.ID_WORKER);  // 主键策略
        gc.setIdType(IdType.INPUT);     // 该类型可以通过自己注册自动填充插件进行填充(用户自动填充)
        gc.setDateType(DateType.ONLY_DATE);  // 定义生成的实体类中日期类型
        // gc.setSwagger2(true);  // 开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName(driverClassName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();

        // 包: com.loiterer.listener. + letter
        pc.setParent("com.loiterer.listener");
        pc.setModuleName("letter");  // 模块名

        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(
                "t_draft_box",
                "t_envelope_style",
                "t_recipient_box",
                "t_writer_box"
        );  // 表名
        strategy.setNaming(NamingStrategy.underline_to_camel);  // 数据库表映射到实体的命名策略
        strategy.setTablePrefix("t" + "_");  // 生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);  // 数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true);  // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true);  // restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true);  // url中驼峰转连字符

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }
}
