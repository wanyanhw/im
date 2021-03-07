# Springboot 整合 Redis 框架

## 1. pom.xml引入依赖
    <!--redis依赖包-->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
## 2. 在application.properties 配置文件配置 Redis 数据库连接信息
    #redis配置
    #Redis服务器地址
    spring.redis.host=127.0.0.1
    #Redis服务器连接端口
    spring.redis.port=6379
    #Redis数据库索引（默认为0）
    spring.redis.database=0
## 3. 配置 RedisTemplate
    package com.wanyan.util.redis;
    
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.data.redis.connection.RedisConnectionFactory;
    import org.springframework.data.redis.core.RedisTemplate;
    
    @Configuration
    public class RedisConfig {
        @Bean
        public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
            RedisTemplate<K, V> template = new RedisTemplate<>();
            template.setConnectionFactory(redisConnectionFactory);
            return template;
        }
    }