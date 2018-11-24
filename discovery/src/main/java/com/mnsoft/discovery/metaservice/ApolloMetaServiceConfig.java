package com.mnsoft.discovery.metaservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Author by hiling, Email admin@mn-soft.com, Date on 11/24/2018.
 *
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackageClasses = ApolloMetaServiceConfig.class)
public class ApolloMetaServiceConfig {

}