package com.example.recipesapp.config;

import com.example.recipesapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(getEncoder());
        auth.inMemoryAuthentication()
                .withUser("user1").password(getEncoder().encode("user1")).roles()
                .and().passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/actuator/shutdown").permitAll()
                .mvcMatchers("/api/register").permitAll()
                .mvcMatchers("/api/user/{username}").authenticated()
                .mvcMatchers("/", "/public").permitAll()
                .mvcMatchers("/**").authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }
}
