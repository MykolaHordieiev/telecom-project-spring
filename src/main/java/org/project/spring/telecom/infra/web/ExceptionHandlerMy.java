package org.project.spring.telecom.infra.web;

import lombok.extern.log4j.Log4j2;
import org.project.spring.telecom.infra.exception.TelecomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j2
public class ExceptionHandlerMy {

    @ExceptionHandler(TelecomException.class)
    public ModelAndView handleTelecomException(HttpServletRequest request, Exception ex) {
        log.error("Request: " + request.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/ex.jsp");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleRuntimeException(HttpServletRequest request, Exception ex) {
        log.error("Request: " + request.getRequestURL() + " raised " + ex);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/internalerror.jsp");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
}
