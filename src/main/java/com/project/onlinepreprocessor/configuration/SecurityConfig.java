package com.project.onlinepreprocessor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception
	{
		auth.jdbcAuthentication()
		.usersByUsernameQuery("select username, password, confirmed_account from user where username=?")
		.authoritiesByUsernameQuery("select u.username, p.perm_name from user u inner join user_perm ur on(u.username=ur.user) inner join permission p on(ur.perm_id=p.id) where username=?")
		.dataSource(dataSource)
		.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/accountconfirmation").permitAll()
                .antMatchers("/dataset/public/**").permitAll()
                .antMatchers("/user/**").hasAuthority("Admin").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/").failureUrl("/")
                .defaultSuccessUrl("/user/main")
                .usernameParameter("username")
                .passwordParameter("password");
}

	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
}
}