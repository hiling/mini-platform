package com.mnsoft.oauth.config;

import com.alibaba.druid.pool.DruidDataSource;
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
 * Author by hiling, Email admin@mn-soft.com, Date on 11/29/2018.
 */
@Configuration
@MapperScan(basePackages =  "com.mnsoft.oauth.modules.user.mapper", sqlSessionFactoryRef = "sqlSessionFactoryBeanForUser")
public class MyBatisUserConfig {

    @Bean(name = "dataSourceUser")
    @ConfigurationProperties(prefix = "spring.datasource.user")
    public DataSource dataSourceUser() {
        return new DruidDataSource();
//        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBeanForUser(@Qualifier("dataSourceUser") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.mnsoft.oauth.modules.user.model");

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        return factory.getObject();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBeanForUser");
        mapperScannerConfigurer.setBasePackage("com.mnsoft.oauth.modules.user.mapper");
        return mapperScannerConfigurer;
    }
}
