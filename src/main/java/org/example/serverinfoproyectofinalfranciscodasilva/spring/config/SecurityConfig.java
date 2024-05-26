package org.example.serverinfoproyectofinalfranciscodasilva.spring.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true,
        jsr250Enabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    /*@Bean  //todo descomenta las lineas para activar la autenticacion
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
               *//* .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class).build();*//*
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/upload").permitAll()
                                .requestMatchers("/download/**").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/clients/**").permitAll()
                                .requestMatchers("/accountant/**").permitAll()
                                .requestMatchers("/balances/**").permitAll()
                                .requestMatchers("/files/**").permitAll()
                                .anyRequest().authenticated()
                ).build();
    }*/

    //todo quita los paths que si deban autentificarse
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/upload").permitAll()
                                .requestMatchers("/download/**").permitAll()
                                .requestMatchers("/users/**").permitAll()
                                .requestMatchers("/clients/**").permitAll()
                                .requestMatchers("/accountant/**").permitAll()
                                .requestMatchers("/balances/**").permitAll()
                                .requestMatchers("/files/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
