package io.damru.openfeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Add as Configuration in FeignClient if you want to forward the Jwt token automatically
 */
public class JwtForward {

    @Bean
    public RequestInterceptor jwtForwardRequestInterceptor(RequestTemplate requestTemplate) {
        return requestTemplate1 -> {
            ServletRequestAttributes requestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return;
            }
            HttpServletRequest request = requestAttributes.getRequest();
            if (request == null) {
                return;
            }

            String authorization = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(authorization)) {
                requestTemplate.header("Authorization", String.format("Bearer %s", authorization));
            }
        };
    }
}
