# Mini-Platform轻量级微服务治理平台

- Mini-Platform致力于打造更简洁易用的轻量级微服务治理平台，更易于使用与运维；
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
## OAuth
- 为了更好的掌控与易用，OAuth采用自研实现。
- GrantType支持password、client_credentials、refresh_token。
- Token支持延迟吊销、滑动过期和绝对过期。
- 用户名密码验证支持直连数据库验证和调用远程服务验证。

---
## OAuth-Client
- 使用OAuth的客户端使用；
- 客户端引用后，只需要继承BaseController便可方便的获取用户信息；
- 参考示例：[UserController.java](https://github.com/hiling/mini-platform/blob/master/modules/user/src/main/java/com/mnsoft/user/controller/UserController.java)

---
## 其他技术介绍
- Lombok 是一种 Java™ 实用工具，可用来帮助开发人员消除 Java 的冗长，
尤其是对于简单的 Java 对象（POJO）。它通过注解实现这一目的。
  - 项目地址：https://www.projectlombok.org/ <br>
  - 中文介绍：https://blog.csdn.net/motui/article/details/79012846

- MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，
在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。
   - 项目地址：http://mp.baomidou.com/

- Prometheus 是一套开源的新一代Metrics系统监控报警框架，是CNCF中重要的一员，它将所有信息都存储为时间序列数据；因此实现一种Profiling监控方式，实时分析系统运行的状态、执行时间、调用次数等，以找到系统的热点，为性能优化提供依据。可对核心业务指标、应用指标、系统指标等做高效的监控，可与Grafana结合打造出优秀的监控平台。

- 使用Spring Boot Maven Plugin & Apache Maven Dependency Plugin将外部依赖jar与项目分离，
解决发布包过大问题。部署时可以将外部依赖包先上传至服务器，启动时需要使用参数-Dloader.path="lib/"加载外部依赖的jar包，
当依赖的外部jar包未更新时，不需要每次给服务器上传。
项目地址：
  - https://docs.spring.io/spring-boot/docs/current/maven-plugin/
  - http://maven.apache.org/components/plugins/maven-dependency-plugin/
