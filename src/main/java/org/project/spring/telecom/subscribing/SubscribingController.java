package org.project.spring.telecom.subscribing;

import org.project.spring.telecom.rate.Rate;
import org.project.spring.telecom.rate.dto.RateAddSubscribingDTO;
import org.project.spring.telecom.rate.RateService;
import org.project.spring.telecom.subscriber.Subscriber;
import org.project.spring.telecom.subscriber.dto.SubscriberAddSubscribingDTO;
import org.project.spring.telecom.subscriber.SubscriberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SubscribingController {

    private final SubscribingService subscribingService;
    private final SubscriberService subscriberService;
    private final RateService rateService;

    public SubscribingController(SubscribingService subscribingService, SubscriberService subscriberService,
                                 RateService rateService) {
        this.subscribingService = subscribingService;
        this.subscriberService = subscriberService;
        this.rateService = rateService;
    }

    @PostMapping("/add/subscribing")
    public RedirectView addSubscribing(HttpServletRequest request, RedirectAttributes attributes) {
        Long productId = Long.parseLong(request.getParameter("productId"));
        Long rateId = Long.parseLong(request.getParameter("rateId"));
        SubscriberAddSubscribingDTO subscriberDTO = getSubscriberDTO(request);
        RateAddSubscribingDTO rateDTO = getRateDTO(rateId);
        SubscriberAddSubscribingDTO returnedSubscriberDTO = subscribingService.addSubscribing(subscriberDTO, productId, rateDTO);
        attributes.addAttribute("id", returnedSubscriberDTO.getId());
        if (returnedSubscriberDTO.getBalance() < 0) {
            subscriberService.lockSubscriberById(returnedSubscriberDTO.getId());
            return new RedirectView("/telecom/subscriber/lock.jsp");
        }
        return new RedirectView("/telecom/service/subscriber");
    }

    private RateAddSubscribingDTO getRateDTO(Long rateId) {
        Rate rate = rateService.getRateById(rateId);
        return new RateAddSubscribingDTO(rateId, rate.getPrice());
    }

    private SubscriberAddSubscribingDTO getSubscriberDTO(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Subscriber subscriberFromSession = (Subscriber) session.getAttribute("user");
        Subscriber subscriberById = subscriberService.getSubscriberById(subscriberFromSession.getId());
        return new SubscriberAddSubscribingDTO(subscriberById.getId(), subscriberById.getBalance());
    }
}
