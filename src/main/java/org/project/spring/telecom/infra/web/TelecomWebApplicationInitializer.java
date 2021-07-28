package org.project.spring.telecom.infra.web;

import org.project.spring.telecom.Application;
import org.project.spring.telecom.infra.auth.AuthorizationFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TelecomWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(Application.class);

        container.addListener(new ContextLoaderListener(root));
        container.addListener(buildLocaleSessionListener());

        FilterRegistration.Dynamic auth = container.addFilter("auth", new AuthorizationFilter());
        auth.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic encoding = container.addFilter("encoding", new CharacterEncodingFilter("UTF-8"));
        encoding.addMappingForUrlPatterns(null, false, "/*");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(new GenericWebApplicationContext());
        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/service/*");
    }

    private LocaleSessionListener buildLocaleSessionListener() {
        List<Locale> locales = new ArrayList<>();
        Locale selectedLocale = new Locale("en");
        locales.add(selectedLocale);
        locales.add(new Locale("ru"));
        return new LocaleSessionListener(locales, selectedLocale);
    }

}
