package com.example.projects.auth.config;


import com.example.projects.auth.security.JwtToUserConverter;
import com.example.projects.auth.security.KeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

    private final JwtToUserConverter jwtToUserConverter;
    private final KeyUtils keyUtils;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Lazy
    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Configuring security filter chain.");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/register").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/actuator").permitAll()
                        .requestMatchers("/actuator/*").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()
                        .requestMatchers("/api/v1/auth/admin/*").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .oauth2ResourceServer((oauth2) ->
                        oauth2.jwt((jwt) -> jwt.jwtAuthenticationConverter(jwtToUserConverter)))

                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtRequestFilter, BearerTokenAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**", "/resources/**", "/favicon.ico");
    }

    @Bean
    public JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder.withPublicKey(keyUtils.getAccessTokenPublicKey()).build();
    }

    @Bean
    public JwtEncoder jwtAccessTokenEncoder() {
        JWK jwk = new RSAKey.Builder(keyUtils.getAccessTokenPublicKey())
                .privateKey(keyUtils.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(JwtDecoder jwtDecoder) {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtDecoder);
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }

}
