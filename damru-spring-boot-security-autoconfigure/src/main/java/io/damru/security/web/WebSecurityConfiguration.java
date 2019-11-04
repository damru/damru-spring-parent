package io.damru.security.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@EnableWebSecurity
@Order(95)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${app.http.cors.allowedOrigins:*}")
    private String[] allowedOrigins;

    @Value("${app.http.csrf.enabled:true}")
    private boolean csrfEnabled;

    @Value("${app.http.security.deny-by-default:true}")
    private boolean denied;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        cors(http);
        csrf(http);
        defaultPolicy(http);
    }

    private void defaultPolicy(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        if (!denied) {
            http.authorizeRequests().anyRequest().permitAll();
        }
    }

    private void csrf(HttpSecurity http) throws Exception {
        if (!this.csrfEnabled) {
            http.csrf().disable();
        }
    }

    private void cors(HttpSecurity http) throws Exception {
        http.cors();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

    private CorsConfiguration corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        return configuration;
    }
}
