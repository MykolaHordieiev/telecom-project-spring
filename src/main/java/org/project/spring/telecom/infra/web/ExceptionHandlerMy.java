package org.project.spring.telecom.infra.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.project.spring.telecom.infra.exception.TelecomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerMy {

    private static Logger logger = LogManager.getLogger(ExceptionHandlerMy.class);

    @ExceptionHandler(TelecomException.class)
    public ModelAndView handleTelecomeException(HttpServletRequest request, Exception ex) {
        logger.error("Request: " + request.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/ex.jsp");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(HttpServletRequest request, Exception ex) {
        logger.error("Request: " + request.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/internalerror.jsp");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}
