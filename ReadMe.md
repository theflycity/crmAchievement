#### 项目简介

基于RBAC的CRM的后端小项目(目前不完整)

技术栈 java 8、 Spring Boot 2.7.18 、 Mybatis-Plus、 JWT、Spring Security、Redis 7.4、MySQL 8.0

启动项目:

1. 克隆代码到本地,idea导入项目

2. 在本地或远程启动Redis7.4、MySQL8.0之后,修改`crmAchievement\src\main\resources` 路径下的`application.yml` 配置文件,将数据源修改为你的数据源. 导入**创建表和插入测试数据**的sql语句

3. 点击`crmAchievement\src\main\java\com\example\crmachievement`路径下`AchievementApplication.java`启动类的运行按钮或 `idea`工具栏的运行按钮即可