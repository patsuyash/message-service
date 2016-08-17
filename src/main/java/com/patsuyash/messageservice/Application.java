package com.patsuyash.messageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }



    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        public static final String ADMIN_ROLE_NAME = "ADMIN";
        public static final String USER_ROLE_NAME = "USER";

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            auth.inMemoryAuthentication().
                    withUser("suyash").password("patil").roles(USER_ROLE_NAME, ADMIN_ROLE_NAME).and().
                    withUser("dom").password("parker").roles(USER_ROLE_NAME, ADMIN_ROLE_NAME);
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.httpBasic().and().authorizeRequests().
                    antMatchers(HttpMethod.GET, "/message").hasRole(USER_ROLE_NAME).
                    antMatchers(HttpMethod.POST, "/message").hasRole(ADMIN_ROLE_NAME).
                    antMatchers(HttpMethod.GET, "/message/**").hasRole(USER_ROLE_NAME).
                    antMatchers(HttpMethod.POST, "/message/**").hasRole(ADMIN_ROLE_NAME).and().
                    csrf().disable();
        }
    }
}
