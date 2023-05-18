package com.naturecode.explore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
  // @Bean
  // public WebSecurityCustomizer webSecurityCustomizer() {
  // return (web) -> web.ignoring().requestMatchers("/swagger-ui/index.html",
  // "/v3/**");
  // }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers("/actuator/**", "/v3/**", "/swagger-ui/**").permitAll()
            .anyRequest().authenticated())
        .csrf(csrf -> csrf.disable())
        .httpBasic(withDefaults());
    return http.build();
  }

}