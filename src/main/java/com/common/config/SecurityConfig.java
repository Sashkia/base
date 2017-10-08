package com.common.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
              .usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USER WHERE USERNAME=?")
              .authoritiesByUsernameQuery("SELECT U.USERNAME, A.AUTHORITY\n" +
              "        \t FROM AUTHORITIES A, USER U WHERE U.USERNAME = A.USERNAME AND U.USERNAME = ?");;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      .antMatchers("/index/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/index/**").access("hasRole('ROLE_SUPERUSER')")
//              .anyRequest().authenticated() //all requests will checked
              .and().formLogin().defaultSuccessUrl("/index", false)
              .and()
              .formLogin().loginPage("/log.jsp").permitAll().usernameParameter("j_username")
              .passwordParameter("j_password").loginProcessingUrl("/j_spring_security_check").failureUrl("/log")
              .and()
              .httpBasic()
              .and()
              .authorizeRequests().antMatchers("/security/**").hasRole("ADMIN")
              .antMatchers("/user/**").hasRole("USER")
              .and()
              .logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/log")
              .and()
              .rememberMe().key("myKey").tokenValiditySeconds(300)
              .and()
              .csrf().disable();
  }
}
