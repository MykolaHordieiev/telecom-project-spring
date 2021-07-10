package org.project.spring.telecom.rate;

import lombok.SneakyThrows;
import org.project.spring.telecom.infra.web.QueryValueResolver;
import org.project.spring.telecom.rate.dto.RateAddRequestDTO;
import org.project.spring.telecom.rate.dto.RateChangeRequestDTO;
import org.project.spring.telecom.user.User;
import org.project.spring.telecom.user.UserRole;
import org.project.spring.telecom.product.ProductService;
import org.project.spring.telecom.subscriber.Subscriber;
import org.project.spring.telecom.subscriber.SubscriberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Long.parseLong;

@Controller
public class RateController {

    private final RateService rateService;
    private final SubscriberService subscriberService;
    private final ProductService productService;
    private final RateValidator validator;
    private final QueryValueResolver queryValueResolver;

    public RateController(RateService rateService, SubscriberService subscriberService, ProductService productService,
                          RateValidator validator, QueryValueResolver queryValueResolver) {
        this.rateService = rateService;
        this.subscriberService = subscriberService;
        this.productService = productService;
        this.validator = validator;
        this.queryValueResolver = queryValueResolver;
    }

    @GetMapping("/rate/product")
    public ModelAndView getAllRates(HttpServletRequest request) {
        Subscriber subscriberFromSession = getSubscriberFromSession(request);
        Long productId = parseLong(request.getParameter("productId"));
        List<Rate> ratesByProductId = rateService.getRatesByProductId(productId);
        List<Rate> usingRatesBySubscriber = rateService.getRatesBySubscriberId(subscriberFromSession.getId());
        ModelAndView modelAndView = new ModelAndView("/rate/byproduct.jsp");
        modelAndView.addObject("rates", ratesByProductId);
        modelAndView.addObject("productId", productId);
        modelAndView.addObject("subscriber", subscriberFromSession);
        modelAndView.addObject("ratesMap", getMapWithRates(ratesByProductId, usingRatesBySubscriber));
        return modelAndView;
    }

    private Map<Rate, Boolean> getMapWithRates(List<Rate> ratesByProductId, List<Rate> ratesUsingBySubscriber) {
        Map<Rate, Boolean> ratesMap = new HashMap<>();
        for (Rate rate : ratesByProductId) {
            Boolean check = ratesUsingBySubscriber.stream()
                    .anyMatch(rate1 -> rate.equals(rate1));
            ratesMap.put(rate, check);
        }
        return ratesMap;
    }

    private Subscriber getSubscriberFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user.getUserRole().equals(UserRole.SUBSCRIBER)) {
            Subscriber subscriber = (Subscriber) user;
            return subscriberService.getSubscriberById(subscriber.getId());
        }
        return new Subscriber();
    }

    @GetMapping("/rate/info")
    public ModelAndView getRateById(HttpServletRequest request, ModelMap model) {
        long id = parseLong(request.getParameter("rateId"));
        Rate rate = rateService.getRateById(id);
        ModelAndView modelAndView = new ModelAndView("/rate/byid.jsp");
        modelAndView.addObject("rate", rate);
        return modelAndView;
    }

    @PostMapping("/rate")
    public ModelAndView changeRates(HttpServletRequest request) {
        String method = request.getParameter("method");
        if (method.equals("PUT")) {
            RateChangeRequestDTO rateDTO = queryValueResolver.getObject(request, RateChangeRequestDTO.class);
            validator.checkEmptyEntryChangeParameter(rateDTO);
            rateService.changeRateById(rateDTO);
            return getAllRates(request);
        }
        return deleteRate(request);
    }

    public ModelAndView deleteRate(HttpServletRequest request) {
        long rateId = parseLong(request.getParameter("rateId"));
        Rate rate = rateService.getRateById(rateId);
        List<Subscriber> subscriberList = rateService.checkUsingRateBeforeDelete(rateId);
        if (subscriberList.isEmpty()) {
            rateService.deleteRateById(rateId);
            return getAllRates(request);
        }
        rate = rateService.doUnusableRate(rate);
        ModelAndView modelAndView = new ModelAndView("/rate/unusable.jsp");
        modelAndView.addObject("rate", rate);
        modelAndView.addObject("subscribers", subscriberList);
        return modelAndView;
    }

    @GetMapping("/rate/add")
    public ModelAndView returnViewAddRates(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/rate/add.jsp");
        modelAndView.addObject("productId", request.getParameter("productId"));
        return modelAndView;
    }

    @PostMapping("/rate/add")
    public ModelAndView addRate(HttpServletRequest request) {
        RateAddRequestDTO rateDTO = queryValueResolver.getObject(request, RateAddRequestDTO.class);
        validator.checkEmptyEntryAddParameter(rateDTO);
        rateService.addRateForProduct(rateDTO);
        return getAllRates(request);
    }

    @GetMapping("/download/rate")
    @SneakyThrows
    public HttpServletResponse downloadListOfRates(HttpServletRequest request, HttpServletResponse response) {
        Long productId = parseLong(request.getParameter("productId"));
        String productName = productService.getProductById(productId).getName();
        List<Rate> rates = rateService.getRatesByProductId(productId);
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=rates.txt");
        PrintWriter writer = response.getWriter();
        writer.write("Information about rates of product: " + productName);
        writer.write("\n");
        for (Rate rate : rates) {
            writer.write("Name: ");
            writer.write(rate.getName());
            writer.write(" Price: ");
            writer.write(rate.getPrice().toString());
            writer.write("\n");
        }
        writer.close();
        return response;
    }
}
