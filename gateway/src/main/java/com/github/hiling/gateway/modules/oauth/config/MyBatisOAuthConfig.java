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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/29/2018.
 */
@Configuration
@MapperScan(basePackages =  "com.github.hiling.gateway.modules.oauth.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBeanForOAuth")
public class MyBatisOAuthConfig {

    @Primary
    @Bean(name = "dataSourceOAuth")
    @ConfigurationProperties(prefix = "spring.datasource.oauth")
    public DataSource dataSourceOAuth() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactoryBeanForOAuth(@Qualifier("dataSourceOAuth") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.github.hiling.gateway.modules.oauth.model");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factory.getObject();
    }

    @Bean
    @Primary
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanForOAuth");
        mapperScannerConfigurer.setBasePackage("com.github.hiling.gateway.modules.oauth.mapper");
        return mapperScannerConfigurer;
    }
}
