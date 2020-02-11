package org.strep.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;
import org.strep.domain.Permission;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * Password encrypter
     */
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * The source of the data, in this case the database
     */
	@Autowired
	private DataSource dataSource;

    /**
     * This method stablish an jdbc based authentication
     */
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception
	{
		auth.jdbcAuthentication()
        .usersByUsernameQuery("SELECT username, encrypted_password, confirmed_account FROM user WHERE username=?")
        .authoritiesByUsernameQuery("SELECT user.username,permission.name FROM user, permission WHERE permission.id <= user.permission_id AND user.username=?")
		//.authoritiesByUsernameQuery("select u.username, p.name from user u inner join user_perm ur on(u.username=ur.user) inner join permission p on(ur.perm_id=p.id) where username=?")
		.dataSource(dataSource)
		.passwordEncoder(bCryptPasswordEncoder);
	}

    /**
     * This method configure the authorized requests based on the authorities of the session user
     */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
                http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/accountconfirmation").permitAll()
                .antMatchers("/dataset/public/**").permitAll()
                .antMatchers("/user/editProfile/**","/dataset/list/**", "/dataset/system/**","/dataset/protected/**", "/permission/**","/permission/solicit/**").hasAnyAuthority(Permission.VIEW,Permission.CREATE_CORPUS,Permission.UPLOAD,Permission.ADMINISTER)
                .antMatchers("/dataset/create/**", "/task/create/**").hasAnyAuthority(Permission.CREATE_CORPUS, Permission.UPLOAD, Permission.ADMINISTER)
                .antMatchers("/dataset/upload/**", "/task/upload/**").hasAnyAuthority(Permission.UPLOAD, Permission.ADMINISTER)
                .antMatchers("/license/list/**", "/license/add/**", "/user/list/**", "/permission/listrequests/**", "/user/detailed/**", "/user/delete/**", 
                "/license/modify/**")
                .hasAuthority(Permission.ADMINISTER).anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/").failureUrl("/?error=true")
                .defaultSuccessUrl("/dataset/list")
                .usernameParameter("username")
                .passwordParameter("password");
    }

    /**
     * This method configure serve allways the specified resources directories
     */
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}