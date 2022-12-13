package com.memoire.kital.raph.openFeign.intercepter;

import com.memoire.kital.raph.security.SecurityUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import springfox.documentation.service.OAuth;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = SecurityUtils.getJwtToken();
        if(null != token && !token.trim().isEmpty()) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
