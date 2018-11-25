#Mini-Platform轻量级微服务治理平台

- Mini-Platform致力于打造更简洁易用的轻量级微服务治理平台，更易于使用与运维；
- 核心技术：SpringBoot、Spring Cloud、Apollo、OAuth（自研）、MyBatis、Redis、MySQL；
- 核心功能：服务注册与发现、服务网关、负载均衡、统一认证、配置中心、异常处理等；

## Discovery
- 采用Eureka做服务自动注册与发现；
- 支持Apollo的MetaService，可以让Apollo共享该Eureka:
- 可按配置简单的进行高可用部署；

## Gateway
- 采用Zuul做服务网关；
- 支持Apollo进行动态路由配置:
- 支持基于service-id的动态路由策略，默认支持负载均衡，业务服务基于SpringCloud时可使用；
- 支持基于url的动态路由策略，使用ribbon做负载均衡，业务服务基于传统HTTP调用时可使用，推荐；
- 支持基于默认url的动态路由策略，不支持负载均衡，业务服务需要单独处理负载均衡，如Nginx；

## OAuth
- 为了更好的掌控与易用，OAuth采用自研实现。
- GrantType支持password、client_credentials、refresh_token。
- Token支持延迟吊销、滑动过期与绝对过期。
