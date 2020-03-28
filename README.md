使用Spring管理Beans和事务, 使用Spring-JdbcTemplate进行数据库操作的基于Servlet的web超市管理系统.

数据库: mySQL-5.7

默认容器: tomcat-9.0.31

依赖: fastJSON, freeMarker, Spring

数据库构建文件: `files/buildDatabase.sql`

编译和启动方式
```shell
mvn clean package
```

输出war包位于target/myShoppingManagement-xxx.war

注: 页面前端部分没有完全实现完, 主要是ajax和js逻辑, 后端除页面外遵循Restful接口规范. API测试位于`test/`目录下

相关技术知识点:
- Spring-Ioc基于注解和xml的配置
- Spring事务管理
- Spring-AOP基于注解的log管理
- spring-jdbcTemplate关联mySQL数据源的配置
- web.xml中配置Spring的ApplicationContext
- Spring向HttpServlet类自动注入