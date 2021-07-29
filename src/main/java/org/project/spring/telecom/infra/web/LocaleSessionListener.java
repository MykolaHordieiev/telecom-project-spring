package org.project.spring.telecom.infra.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Log4j2
public class LocaleSessionListener implements HttpSessionListener {

    private final List<Locale> locales;
    private final Locale selectedLocale;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        log.info("Session created");

        session.setAttribute("locales", locales);
        log.info("Set session locales --> " + locales);

        session.setAttribute("selectedLocale", selectedLocale);
        log.info("Set session selected locale --> " + selectedLocale);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("session destroyed");
    }
}
