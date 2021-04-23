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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.wanyan.core.mapper", sqlSessionFactoryRef = "imSqlSessionFactory")
public class ImDataSourceConfig {

    @Bean("imDataSource")
    @ConfigurationProperties("spring.datasource.im")
    public DataSource imDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("imSqlSessionFactory")
    public SqlSessionFactory imSqlSessionFactory(@Qualifier("imDataSource") DataSource dataSource) {
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

    @Bean
    public DataSourceTransactionManager imTransactionManager(@Qualifier("imDataSource") DataSource imDataSource) {
        return new DataSourceTransactionManager(imDataSource);
    }
}
