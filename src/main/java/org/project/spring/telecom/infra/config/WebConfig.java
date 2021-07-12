package org.project.spring.telecom.infra.config;

import lombok.extern.log4j.Log4j2;
import org.project.spring.telecom.infra.auth.AuthorizationFilter;
import org.project.spring.telecom.infra.encoding.EncodingFilter;
import org.project.spring.telecom.infra.web.LocaleSessionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
@Log4j2
@EnableWebMvc
public class WebConfig {

    @Bean
    public LocaleSessionListener localeSessionListener() {
        List<Locale> locales = new ArrayList<>();
        Locale selectedLocale = new Locale("en");
        locales.add(new Locale("en"));
        locales.add(new Locale("ru"));

        return new LocaleSessionListener(locales, selectedLocale);
    }

    @Bean
    public Filter encodingFilter() {
        return new EncodingFilter();
    }

    @Bean
    public Filter securityFilter() {
        return new AuthorizationFilter();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        return new InternalResourceViewResolver();
    }

}