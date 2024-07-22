package com.frost.springular.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final AuthenticationProvider authenticationProvider;
  private final AuthenticationFilter jwtAuthFilter;
  private final CorsFilter corsFilter;

  @Value("${spring.security.cors.allowed-origins}")
  private String allowedOrigins;

  public SecurityConfig(AuthenticationProvider authenticationProvider,
      AuthenticationFilter jwtAuthFilter,
      CorsFilter corsFilter) {
    this.authenticationProvider = authenticationProvider;
    this.jwtAuthFilter = jwtAuthFilter;
    this.corsFilter = corsFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
      throws Exception {
    httpSecurity.csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers("/api/auth/**")
            // .hasRole("USER")
            // need this in the future
            .permitAll()
            .anyRequest()
            .authenticated())
        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter,
            UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(corsFilter, ChannelProcessingFilter.class);

    return httpSecurity.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();

    config.setAllowedOrigins(List.of(allowedOrigins.split(",")));
    config.setAllowedMethods(
        List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    config.setAllowCredentials(true);

    // config.applyPermitDefaultValues();

    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", config);

    return source;
  }
}
