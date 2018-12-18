package com.github.hiling.gateway.modules.oauth.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 12/1/2018.
 */
@Configuration
@MapperScan(basePackages =  "com.github.hiling.gateway.modules.oauth.modules.client.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBeanForClient")
public class MyBatisClientConfig {

    @Bean(name = "dataSourceClient")
    @ConfigurationProperties(prefix = "spring.datasource.client")
    public DataSource dataSourceUser() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBeanForClient(@Qualifier("dataSourceClient") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.github.hiling.gateway.modules.oauth.modules.client.model");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factory.getObject();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanForClient");
        mapperScannerConfigurer.setBasePackage("com.github.hiling.gateway.modules.oauth.modules.client.mapper");
        return mapperScannerConfigurer;
    }
}
