/*-
 * #%L
 * STRep
 * %%
 * Copyright (C) 2019 - 2024 SING Group (University of Vigo)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.strep.configuration;

import jakarta.servlet.DispatcherType;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.strep.domain.Permission;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    /**
     * This method stablish an jdbc based authentication. The password encoder
     * is the BCryptPasswordEncoder bean defined in WebMvcConfig
     * @param dataSource the source of the data, in this case the database
     * @return the user details service
     */
    @Bean
    public JdbcUserDetailsManager userDetailsService(DataSource dataSource)
    {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("SELECT username, encrypted_password, confirmed_account FROM user WHERE username=?");
        users.setAuthoritiesByUsernameQuery("SELECT user.username,permission.name FROM user, permission WHERE permission.id <= user.permission_id AND user.username=?");
        return users;
    }

    /**
     * This method configure the authorized requests based on the authorities of the session user
     * @param http the http security builder
     * @return the security filter chain
     * @throws Exception if the configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Error pages and forwards were not secured with authorizeRequests() in Spring Security 5
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                // Resources that were ignored with WebSecurity in Spring Security 5
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/webjars/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/changeLang").permitAll()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers("/user/register").permitAll()
                .requestMatchers("/user/accountconfirmation").permitAll()
                .requestMatchers("/dataset/public/**").permitAll()
                .requestMatchers("/user/editProfile/**","/dataset/list/**", "/dataset/system/**","/dataset/protected/**", "/permission/**","/permission/solicit/**").hasAnyAuthority(Permission.VIEW,Permission.CREATE_CORPUS,Permission.UPLOAD,Permission.ADMINISTER)
                .requestMatchers("/dataset/create/**", "/task/create/**").hasAnyAuthority(Permission.CREATE_CORPUS, Permission.UPLOAD, Permission.ADMINISTER)
                .requestMatchers("/dataset/upload/**", "/task/upload/**").hasAnyAuthority(Permission.UPLOAD, Permission.ADMINISTER)
                .requestMatchers("/license/list/**", "/license/add/**", "/user/list/**", "/permission/listrequests/**", "/user/detailed/**", "/user/delete/**",
                "/license/modify/**").hasAuthority(Permission.ADMINISTER)
                .anyRequest().authenticated())
            // TODO: CSRF stays disabled until the forms include the token (phase 4)
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(form -> form
                .loginPage("/").failureUrl("/?error=true")
                .defaultSuccessUrl("/dataset/list")
                .usernameParameter("username")
                .passwordParameter("password"));
        return http.build();
    }
}
