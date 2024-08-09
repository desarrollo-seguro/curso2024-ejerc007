package com.example.ejercicio007;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/public/**").permitAll() // Permitir acceso a URLs que comiencen con /public
                    .anyRequest().authenticated() // Requiere autenticación para cualquier otra URL
            )
            .formLogin(formLogin ->
                formLogin
                    .loginPage("/login") // Página de inicio de sesión personalizada
                    .permitAll()
            )
            .logout(logout ->
                logout
                    .permitAll()
            );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Configura usuarios en memoria
        return new InMemoryUserDetailsManager(
            User.withUsername("user")
                .password("{noop}password") // {noop} indica que la contraseña no está codificada
                .roles("USER")
                .build(),
            User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build()
        );
    }
}


