使用JDBC和Servlet构建的基于web的超市管理系统.

数据库: mySQL-5.7

默认容器: tomcat-9.0.31

依赖: fastJSON, freeMarker

数据库构建文件: `files/buildDatabase.sql`

编译和启动方式
```shell
mvn clean package
```

输出war包位于target/myShoppingManagement-xxx.war

注: 页面前端部分没有完全实现完, 主要是ajax和js逻辑, 后端除页面外遵循Restful接口规范. API测试位于`test/`目录下