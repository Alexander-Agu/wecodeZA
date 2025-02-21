package com.wecodeZA.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable) // Disable CSRF (adjust if needed)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/health", "/api/v1/user/new").permitAll() // Public endpoints
                        .requestMatchers("/**").permitAll()) // Allow all other endpoints (consider changing to .authenticated() for security)
                .httpBasic(Customizer.withDefaults()) // Basic HTTP authentication
                .cors(Customizer.withDefaults()) // Enable CORS in Spring Security
                .build();
    }

    // CORS Configuration with Spring Security Context
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Apply CORS to all endpoints
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // Frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials (cookies)
            }
        };
    }
}
