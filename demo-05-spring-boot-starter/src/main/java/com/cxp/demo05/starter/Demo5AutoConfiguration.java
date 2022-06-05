package com.cxp.demo05.starter;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Demo5Configuration.class)
@EnableConfigurationProperties(StudentProperties.class)
public class Demo5AutoConfiguration {
}
