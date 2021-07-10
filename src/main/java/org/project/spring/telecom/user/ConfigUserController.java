package org.project.spring.telecom.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConfigUserController {

    @Bean
    public Map<UserRole, String> getViewMap() {
        Map<UserRole, String> viewMap = new HashMap<>();
        viewMap.put(UserRole.OPERATOR, "/telecom/operator/home.jsp");
        viewMap.put(UserRole.SUBSCRIBER, "/telecom/subscriber/home.jsp");
        return viewMap;
    }
}
