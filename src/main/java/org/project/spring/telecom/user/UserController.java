package org.project.spring.telecom.user;

import org.project.spring.telecom.infra.web.QueryValueResolver;
import org.project.spring.telecom.user.dto.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final UserValidator validator;
    private final Map<UserRole, String> viewMap;
    private final QueryValueResolver queryValueResolver;

    public UserController(UserService userService, Map<UserRole, String> viewMap, UserValidator validator, QueryValueResolver queryValueResolver) {
        this.userService = userService;
        this.validator = validator;
        this.viewMap = viewMap;
        this.queryValueResolver = queryValueResolver;
    }

    @PostMapping("/change/locale")
    public RedirectView changeLocale(HttpServletRequest request, RedirectAttributes attributes) {
        String selectedLocale = request.getParameter("selectedLocale");
        String view = request.getParameter("view");
        Locale locale = new Locale(selectedLocale);
        HttpSession session = request.getSession(false);
        session.setAttribute("selectedLocale", locale);
        return new RedirectView("/telecom/" + view);
    }

    @PostMapping("/login")
    public RedirectView login(HttpServletRequest request) {
        UserLoginDTO userLoginDTO = queryValueResolver.getObject(request, UserLoginDTO.class);
        validator.checkUser(userLoginDTO);
        User returnedUser = userService.loginUser(userLoginDTO);
        HttpSession session = request.getSession();
        session.setAttribute("user", returnedUser);
        return new RedirectView(viewMap.get(returnedUser.getUserRole()));
    }

    @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new RedirectView("/telecom/index.jsp");
    }
}
