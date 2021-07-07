package org.project.spring.telecom.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigObjectMapper {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
