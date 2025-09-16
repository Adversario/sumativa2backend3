package com.duoc.bff.atm.security;
import org.springframework.context.annotation.*; import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {
  @Bean public JwtService jwtService(){ return new JwtService(); }
  @Bean public SecurityFilterChain filterChain(HttpSecurity http, JwtService jwt) throws Exception {
    http.csrf(csrf->csrf.disable())
      .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth->auth
        .requestMatchers("/auth/login").permitAll()
        .requestMatchers("/api/atm/**").hasRole("ATM_TERMINAL")
        .anyRequest().authenticated())
      .addFilterBefore(new JwtAuthFilter(jwt), UsernamePasswordAuthenticationFilter.class)
      .httpBasic(Customizer.withDefaults());
    return http.build();
  }
}
