package org.project.spring.telecom.subscriber;


import org.project.spring.telecom.infra.web.QueryValueResolver;
import org.project.spring.telecom.subscriber.dto.SubscriberCreateDTO;
import org.project.spring.telecom.subscriber.dto.SubscriberReplenishDTO;
import org.project.spring.telecom.subscribing.SubscribingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final SubscribingService subscribingService;
    private final SubscriberValidator validator;
    private final QueryValueResolver queryValueResolver;

    public SubscriberController(SubscriberService subscriberService, SubscribingService subscribingService,
                                SubscriberValidator validator, QueryValueResolver queryValueResolver) {
        this.subscriberService = subscriberService;
        this.subscribingService = subscribingService;
        this.validator = validator;
        this.queryValueResolver = queryValueResolver;
    }

    @GetMapping("/subscriber")
    public ModelAndView getSubscriberById(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        ModelAndView modelAndView = new ModelAndView("/subscriber/infobyid.jsp");
        modelAndView.addObject("subscriber", subscriberService.getSubscriberById(id));
        modelAndView.addObject("subscriptions", subscribingService.getSubscribing(id));
        return modelAndView;
    }

    @GetMapping("/subscriber/bylogin")
    public ModelAndView getSubscriberByLogin(HttpServletRequest request) {
        String login = validator.checkEmptyLogin(request.getParameter("login"));
        Subscriber foundSubscriber = subscriberService.getSubscriberByLogin(login);
        ModelAndView modelAndView = new ModelAndView("/subscriber/infobyid.jsp");
        modelAndView.addObject("subscriber", foundSubscriber);
        modelAndView.addObject("subscriptions", subscribingService.getSubscribing(foundSubscriber.getId()));
        return modelAndView;
    }

    @PostMapping("/subscriber")
    public RedirectView createSubscriber(HttpServletRequest request, RedirectAttributes attributes) {
        SubscriberCreateDTO dto = queryValueResolver.getObject(request, SubscriberCreateDTO.class);
        validator.checkValidLoginPassword(dto);
        Subscriber createdSubscriber = subscriberService.create(dto);
        attributes.addAttribute("id", createdSubscriber.getId());
        return new RedirectView("/telecom/service/subscriber");
    }

    @GetMapping("/subscriber/all")
    public ModelAndView getAll(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        ModelAndView modelAndView = new ModelAndView("/subscriber/all.jsp");
        modelAndView.addObject("subscribers", subscriberService.getAll(page));
        modelAndView.addObject("countOfHref", subscriberService.getCountOfHref());
        return modelAndView;
    }

    @PostMapping("/subscriber/lock")
    public ModelAndView lockSubscriber(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        subscriberService.lockSubscriberById(id);
        return getSubscriberById(request);
    }

    @PostMapping("/subscriber/unlock")
    public ModelAndView unlockSubscriber(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        subscriberService.unlockSubscriberById(id);
        return getSubscriberById(request);
    }

    @PostMapping("/subscriber/balance")
    public RedirectView replenishBalance(HttpServletRequest request, RedirectAttributes attributes) {
        String stringAmount = request.getParameter("amount");
        Double amount = validator.checkEntryNumber(stringAmount);
        SubscriberReplenishDTO replenishDTO = getSubscriberDTO(request);
        Subscriber returnedSubscriber = subscriberService.replenishBalance(replenishDTO, amount);
        HttpSession session = request.getSession();
        session.setAttribute("user", returnedSubscriber);
        attributes.addAttribute("id", returnedSubscriber.getId());
        return new RedirectView("/telecom/service/subscriber");
    }

    private SubscriberReplenishDTO getSubscriberDTO(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Subscriber subscriberFromSession = (Subscriber) session.getAttribute("user");
        SubscriberReplenishDTO subscriberReplenishDTO = new SubscriberReplenishDTO();
        subscriberReplenishDTO.setId(subscriberFromSession.getId());
        return subscriberReplenishDTO;
    }
}