package io.damru.openfeign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Add as Configuration in FeignClient if you want to forward the Jwt token automatically
 */
public class JwtForward {

    @Bean
    public RequestInterceptor jwtForwardRequestInterceptor() {
        return (requestTemplate) -> {
            ServletRequestAttributes
                    requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                if (request != null) {
                    String bearer = request.getHeader("Authorization");
                    if (bearer != null && bearer.trim().length() > 0) {
                        requestTemplate.header("Authorization", bearer);
                    }
                }
            }
        };
    }
}
