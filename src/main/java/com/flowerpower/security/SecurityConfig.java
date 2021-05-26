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

        auth.jdbcAuthentication().dataSource(dataSource)
                .withDefaultSchema()
                .usersByUsernameQuery("select username, password, true as activated from user where username = ?")
                .rolePrefix("")
                .and();

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/currentuser").permitAll()
            .antMatchers("/order/*").permitAll()
            .antMatchers("/orders").permitAll()
            .antMatchers("/order").permitAll()
            .antMatchers("/register").permitAll()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .antMatchers("/login*").permitAll()

            .antMatchers("/item*").permitAll()
            .antMatchers("/items").permitAll()
            .antMatchers("/item/*").permitAll()

            .antMatchers("/item/*/photo").permitAll()
            .antMatchers("/photo/*").permitAll()
            .antMatchers("/user/exists").permitAll()
                .antMatchers("/currentUserRole").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().and();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
