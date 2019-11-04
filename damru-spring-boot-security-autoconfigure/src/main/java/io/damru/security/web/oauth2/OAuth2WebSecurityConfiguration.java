package io.damru.security.web.oauth2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({
        WebSecurityConfigurerAdapter.class,
        BearerTokenAccessDeniedHandler.class
})
@Order(90)
public class OAuth2WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer().jwt();
    }

}
