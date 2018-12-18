# Mini-Platform轻量级微服务治理平台

- Mini-Platform致力于打造更简洁易用的轻量级微服务治理平台，更易于实施与运维；
- 核心技术：SpringBoot、Spring Cloud、Apollo、OAuth2（自研）、MyBatis、Redis、MySQL；
- 核心功能：服务注册与发现、服务网关、负载均衡、统一认证、配置中心、异常处理等；

---
## Discovery
- 采用Eureka做服务自动注册与发现；
- 支持Apollo的MetaService，可以让Apollo共享该Eureka:
- 可按配置简单的进行高可用部署；

---
## Gateway
- 采用Zuul做服务网关；
- 支持Apollo进行动态路由配置:
- 支持基于service-id的动态路由策略，支持负载均衡，支持服务的**自动**注册与发现，当服务地址变化后**无需手动配置**，当后端服务引入SpringCloud时可选用；
- 支持基于url的动态路由策略，支持负载均衡，支持服务的**手动**注册与发现，当服务地址变化后**需要手动配置**，当后端服务基于传统HTTP调用时可选用；
- 支持基于默认url的动态路由策略，不支持负载均衡，后端服务需要单独处理负载均衡（如Nginx），支持服务的**手动**注册与发现，配置简单，可在测试中使用；
- 支持服务异常重试，建议只开启GET的重试，且确保GET的幂等，否则建议关闭；

---
## Gateway - OAuth  
- Gateway中集成的OAuth2模块
- 为了更简单易用，OAuth Server采用自研实现。
- GrantType支持password、client_credentials、refresh_token。
- Token支持延迟吊销、滑动过期和绝对过期。
- 用户名密码验证支持直连用户中心数据库验证和调用远程服务验证两种方式。
- 密码模式授权，用于客户端与服务器之间的授权，流程如下：
![oauth-password-flow](https://raw.githubusercontent.com/hiling/mini-platform/master/.files/oauth-password-flow.png "密码模式授权流程")
注：图例为三次请求，1.1-1.3为首次认证；2.1-2.5为通过Access Token访问后端资源；3.1-3.3为使用Refresh Token获取新的Access Token，可用于Access Token过期前刷新Token；
红色字体是Password与Client授权方式不同的地方。

- 客户端模式授权，用于服务器与服务器之间的授权，流程如下：
![oauth-client-flow](https://raw.githubusercontent.com/hiling/mini-platform/master/.files/oauth-client-flow.png "客户端模式授权流程")

- 微服务模式授权示例，是获取到Access Token后请求后端资源的流程细化，如下：
![oauth-multi-services-flow](https://raw.githubusercontent.com/hiling/mini-platform/master/.files/oauth-multi-services-flow.png "客户端模式授权流程")

---
## Gateway - ACL
- Gateway中集成的ACL(访问控制列表)模块，对API进行权限控制。
- 待实现

---
## Gateway - RateLimiting  
- Gateway中集成的限流模块，对API进行流量控制。
- 待实现

---
## Gateway - Other  
- Gateway中的Log、Metrics、Trace、Alert、Security、Canary等模块。
- 待实现

---
## OAuth-Client
- 使用OAuth的客户端使用；
- 客户端引用后，只需要继承BaseController便可方便的获取用户信息；
- 参考示例：[UserController.java](https://github.com/hiling/mini-platform/blob/master/modules/user/src/main/java/com/github/hiling/user/controller/UserController.java)

---
## 项目中使用的其他技术介绍
- **Lombok** 是一种 Java™ 实用工具，可用来帮助开发人员消除 Java 的冗长，
尤其是对于简单的 Java 对象（POJO）。它通过注解实现这一目的。
  - 官网地址：https://www.projectlombok.org/ 
  - 项目地址：https://github.com/rzwitserloot/lombok
  - 中文介绍：https://blog.csdn.net/motui/article/details/79012846

- **MyBatis-Plus**（简称 MP）是一个 MyBatis 的增强工具，
在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
   - 官网地址：http://mp.baomidou.com/
   - 项目地址：https://github.com/baomidou/mybatis-plus
   
- **Apollo**（阿波罗）是携程框架部门研发的分布式配置中心，能够集中化管理应用不同环境、不同集群的配置，配置修改后能够实时推送到应用端，并且具备规范的权限、流程治理等特性，适用于微服务配置管理场景。 
  - 项目地址：https://github.com/ctripcorp/apollo

- **Prometheus** 是一套开源的新一代Metrics系统监控报警框架，是CNCF中重要的一员，它将所有信息都存储为时间序列数据；因此实现一种Profiling监控方式，实时分析系统运行的状态、执行时间、调用次数等，以找到系统的热点，为性能优化提供依据。可对核心业务指标、应用指标、系统指标等做高效的监控，可与Grafana结合打造出优秀的监控平台。
   - 官网地址：https://prometheus.io/
   - 项目地址：https://github.com/prometheus

- **Druid** 是阿里巴巴数据库事业部出品，为监控而生的数据库连接池。
  - 项目地址：https://github.com/alibaba/druid
  
- **JMH** 是一个Java的微基准测试框架，精度可以精确到微秒级。
  - 项目地址：http://openjdk.java.net/projects/code-tools/jmh/
  - 官方示例：http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
  - 中文介绍：https://www.xncoding.com/2018/01/07/java/jmh.html
  - 项目中OAuth基准测试代码
  ![create_token_test](https://raw.githubusercontent.com/hiling/mini-platform/master/.files/create_token_test.png "基准测试代码")
  注：测试代码：[UserApplicationTests.java](https://github.com/hiling/mini-platform/blob/master/modules/user/src/test/java/com/github/hiling/user/UserApplicationTests.java)
  - 测试结果
  ![create_token_test_result](https://raw.githubusercontent.com/hiling/mini-platform/master/.files/create_token_test_result.png "基准测试结果")
  注：在开发环境测试，电脑配置2C(i5-6300U)/8G/SSD，本机MySQL，使用相关参数，未考虑DB缓存等影响。
  
- **Spring Boot Maven Plugin & Apache Maven Dependency Plugin** 可以将外部依赖jar与项目分离，
解决发布包过大问题。部署时可以将外部依赖包先上传至服务器，启动时需要使用参数-Dloader.path="lib/"加载外部依赖的jar包，
当依赖的外部jar包未更新时，不需要每次给服务器上传。
项目地址：
  - https://docs.spring.io/spring-boot/docs/current/maven-plugin/
  - http://maven.apache.org/components/plugins/maven-dependency-plugin/

#### 欢迎Star和Fork，微信号：HilingWang，欢迎扫码交流！
  ![wechat-qrcode](https://raw.githubusercontent.com/hiling/mini-platform/master/.files/wechat-qrcode.jpg "微信二维码")
 
