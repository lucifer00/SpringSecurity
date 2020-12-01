package com.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{
  private PasswordEncoder passwordEncoder;

  @Autowired
  public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
       .csrf().disable()
       .authorizeRequests()
       .antMatchers("/", "index", "/css/*", "/js/*")
       .permitAll()
       .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
       .antMatchers(HttpMethod.DELETE, "/management/api/**")
       .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
       .antMatchers(HttpMethod.PUT, "/management/api/**")
       .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
       .antMatchers(HttpMethod.POST, "/management/api/**")
       .hasAuthority(ApplicationUserPermission.STUDENT_WRITE.getPermission())
       .antMatchers(HttpMethod.GET, "/management/api/**")
       .hasAnyRole(ApplicationUserRole.ADMIN.name(),
                   ApplicationUserRole.ADMINTRAINEE.name())
       .anyRequest()
       .authenticated()
       .and()
       .httpBasic();
  }

  @Override
  @Bean
  protected UserDetailsService userDetailsService() {
    UserDetails prashantUser = 
        User
        .builder()
        .username("prashant")
        .password(passwordEncoder.encode("Rishujha#*1"))
        //.roles(ApplicationUserRole.STUDENT.name())
        .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
        .build();
    UserDetails adminUser = 
        User
        .builder()
        .username("sanjay")
        .password(passwordEncoder.encode("Sushantjha#*1"))
        //.roles(ApplicationUserRole.ADMIN.name())
        .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
        .build();
    UserDetails adminTraineeUser = 
        User
        .builder()
        .username("sushant")
        .password(passwordEncoder.encode("password123"))
        //.roles(ApplicationUserRole.ADMINTRAINEE.name())
        .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
        .build();
    return new InMemoryUserDetailsManager(prashantUser, adminUser, adminTraineeUser);
  }
}
