package io.damru.security.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${app.http.web.allowedOrigins:*}")
    private String allowedOrigins;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        cors(http);
    }

    private void cors(HttpSecurity http) throws Exception {
        http.cors().and()
            .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and()
            .oauth2ResourceServer().jwt();
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
