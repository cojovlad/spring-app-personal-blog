package com.example.spring_app_personal_blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/view/articles/**", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll() // Public endpoints
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Restrict admin endpoints to ADMIN role
                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .formLogin(login -> login
                        .loginPage("/login") // Custom login page
                        .defaultSuccessUrl("/admin/dashboard", true) // Redirect to dashboard after successful login
                        .permitAll() // Allow everyone to access the login page
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKey") // Key used to generate the remember-me token
                        .tokenValiditySeconds(86400) // 1 day (in seconds)
                        .rememberMeParameter("remember-me") // Name of the checkbox in the login form
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Custom logout URL
                        .logoutSuccessUrl("/") // Redirect to home page after logout
                        .invalidateHttpSession(true) // Invalidate the session
                        .deleteCookies("JSESSIONID", "remember-me") // Delete cookies on logout
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}