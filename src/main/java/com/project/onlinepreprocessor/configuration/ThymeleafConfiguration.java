package com.project.onlinepreprocessor.configuration;

import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
@Configuration
public class ThymeleafConfiguration {

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}