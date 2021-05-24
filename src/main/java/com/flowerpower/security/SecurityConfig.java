package com.flowerpower.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private FlowerPowerUserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("verysecureadminpassword"))
                .roles("ADMIN")
                .and()
                .withUser("testuser")
                .password(passwordEncoder().encode("verysecureuserpassword"))
                .roles("USER")
                .and();

        auth.jdbcAuthentication().dataSource(dataSource)
                .withDefaultSchema()
                .withUser("db_admin")
                .password(passwordEncoder().encode("verysecureadminpassword"))
                .roles("ADMIN")
                .and()
                .withUser("db_testuser")
                .password(passwordEncoder().encode("verysecureuserpassword"))
                .roles("USER")
                .and();

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/currentuser").permitAll()
            .antMatchers("/order*").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/login*").permitAll()
            .antMatchers("/items").permitAll()
            .antMatchers("/item/*").permitAll()
            .antMatchers("/photo/*").permitAll()
                .antMatchers("/user/exists").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().and();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
