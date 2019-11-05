package io.damru.security.web.oauth2.okta.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public class OktaHelper {

    public static OktaUserClaims getUserClaims() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> claims = ((Jwt) principal).getClaims();
        return new ObjectMapper().convertValue(claims, OktaUserClaims.class);
    }

}
