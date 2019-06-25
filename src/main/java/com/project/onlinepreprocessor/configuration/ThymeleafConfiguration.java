package com.project.onlinepreprocessor.configuration;

import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
@Configuration
public class ThymeleafConfiguration {

    /**
     * Method to return Spring Security Dialect, for session utilities in dynamic HTML generation
     * @return the Spring Security Dialect
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}