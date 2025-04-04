package com.example.Produtos.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para testes, não recomendado para produção
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/produtos/**").permitAll() // Permite requisições para clientes
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}