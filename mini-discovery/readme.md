
## Apollo配置中心使用该eureka服务的配置：
1. 确保项目中metaservice与Apollo对应版本的ConfigService中metaservice的逻辑与接口一致；
2. 修改Apollo中ConfigService的@EnableEurekaServer为@EnableEurekaClient；
3. 修改Apollo中的ConfigService和AdminService的eureka.client.service-url.default-zone为该eureka服务地址；
4. 修改Apollo中的Portal的MetaService地址为该eureka服务地址，如：dev.meta=http://localhost:8761；
5. 修改ApolloConfigDB.serverconfig表中eureka.service.url的值为该eureka服务地址；

## Apollo项目说明
### 项目地址： 
https://github.com/ctripcorp/apollo

### 服务启动：
1. 使用Apollo项目scripts/sql中脚本创建数据库；
2. 修改ConfigService和AdminService中config/application-github.properties中的DB连接字符串,DB为ApolloConfigDB；
3. 修改Portal中config/application-github.properties中的DB连接字符串,DB为ApolloPortalDB；
4. 依次启动ConfigService、AdminService、Portal，注意-Dapollo_profile=github及端口占用；