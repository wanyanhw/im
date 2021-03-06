package com.wanyan.config.datasource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.wanyan.core.mapper", sqlSessionFactoryRef = "queueSqlSessionFactory")
public class QueueDataSourceConfig {

    @Bean("queueDataSource")
    @ConfigurationProperties("spring.datasource.queue")
    public DataSource queueDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("queueSqlSessionFactory")
    public SqlSessionFactory imSqlSessionFactory(@Qualifier("queueDataSource") DataSource dataSource) {
        try {
            MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
