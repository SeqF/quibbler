# the-quibbler
![Travis](https://img.shields.io/badge/JDK-1.11-orange.svg)
![Travis](https://img.shields.io/badge/SpringBoot-2.5.x-brightgreen.svg)

以 Spring Boot 为基础，整合常用中间件以及相关功能的编写，方便日常开发学习使用

### Spring Security
- [x] 基本配置
- [x] 使用 JWT 进行认证
- [x] 使用 Caffeine 缓存用户权限
- [x] 角色权限设计
- [x] 自定义投票器来实现动态鉴权
- [ ] 结合 OAuth2
- [ ] 实现 SSO 单点登录


### Redis
- [x] 基本配置
- [x] 使用 Redis 替代 Caffeine 进行权限缓存
- [x] 计数器的实现
- [x] 热点数据缓存
- [ ] 支持主从复制的工具类（master 写，slave 读）

### RabbitMQ
- [x] 基本配置
- [x] 配置常用队列
- [x] 实现延时队列
- [x] 配置消息限流
- [x] 消息可靠性保证

### Elasticsearch
- [x] 基本配置
- [x] 基础查询操作的工具类编写
- [x] 高亮查询
- [ ] 实现滚动查询
