package com.nhnacademy.edu.jdbc1.web;

import com.nhnacademy.edu.jdbc1.service.login.LoginRequest;
import com.nhnacademy.edu.jdbc1.service.login.User;
import com.nhnacademy.edu.jdbc1.service.login.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserLoginService userLoginService;

    private static final String SESSION = "SESSION";

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        HttpSession httpSession = request.getSession(false);

        if (Objects.nonNull(httpSession) && httpSession.getAttribute(SESSION) != null) {
            String session = httpSession.getAttribute(SESSION).toString();
            model.addAttribute("id", session);
            return "redirect:/";
        } else {
            return "loginForm";
        }
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute LoginRequest request,
                          HttpSession session,
                          ModelMap modelMap) {

        User user = userLoginService.login(request);
        session.setAttribute(SESSION, user.getId());

        modelMap.put("id", session);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (!Objects.isNull(session)) {
            session.removeAttribute(SESSION);
        }
        return "index";
    }
}
