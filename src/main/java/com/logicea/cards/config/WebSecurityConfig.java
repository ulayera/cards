package com.logicea.cards.config;

import com.logicea.cards.repository.UserRepository;
import com.logicea.cards.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final UserRepository repository;

  public WebSecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserRepository repository) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.repository = repository;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());

    // Disable CSRF protection for the API endpoints
    http.csrf()
        .disable();

    // Protect the API endpoints with JWT authentication
    http.authorizeHttpRequests()
        .requestMatchers("/api/auth/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    // No session since we are using JWT
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add JWT authentication filter
    http.authenticationProvider(authProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    // Add logout handler
    http.logout()
        .logoutUrl("/api/v1/auth/logout")
        .logoutSuccessHandler(
            (request, response, authentication) -> SecurityContextHolder.clearContext());

    return http.build();
  }
}
