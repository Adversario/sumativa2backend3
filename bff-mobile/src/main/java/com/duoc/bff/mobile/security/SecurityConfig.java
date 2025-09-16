package com.duoc.bff.mobile.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,
                                         JwtDecoder jwtDecoder,
                                         JwtAuthenticationConverter jwtAuthConverter) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .httpBasic(b -> b.disable())
      .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
        res.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
      }))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/auth/login").permitAll()
        .requestMatchers("/api/mobile/**").hasAuthority("ROLE_MOBILE_USER")
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth -> oauth
        .jwt(jwt -> jwt.decoder(jwtDecoder).jwtAuthenticationConverter(jwtAuthConverter))
      );
    return http.build();
  }

  @Bean
  public JwtDecoder jwtDecoder(@Value("${security.jwt.secret}") String secret) {
    var key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthConverter() {
    JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
    conv.setJwtGrantedAuthoritiesConverter(jwt -> {
      String role = jwt.getClaimAsString("role");
      if (role == null || role.isBlank()) return List.of();
      return List.of(new SimpleGrantedAuthority(role));
    });
    return conv;
  }
}