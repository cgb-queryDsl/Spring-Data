package com.nhnacademy.jdbc.board.user.web;

import com.nhnacademy.jdbc.board.exception.UserNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.user.dto.LoginRequest;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private static final String SESSION = "SESSION";
    private static final String USERNAME = "USERNAME";

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        HttpSession httpSession = request.getSession(false);

        if (Objects.nonNull(httpSession) && httpSession.getAttribute(SESSION) != null) {
            String session = httpSession.getAttribute(SESSION).toString();
            String name = httpSession.getAttribute(USERNAME).toString();
            model.addAttribute("role", session);
            model.addAttribute("username", name);
            return "redirect:/";
        } else {
            return "loginForm";
        }
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute LoginRequest request,
                          BindingResult bindingResult,
                          HttpSession session,
                          ModelMap modelMap) {
        User user = getUser(request, bindingResult);
        session.setAttribute(SESSION, user.getUserRole().toString());
        session.setAttribute(USERNAME, user.getUsername());

        modelMap.put("id", session);

        return "redirect:/";
    }

    private User getUser(LoginRequest request, BindingResult bindingResult) {
        log.info("로그인 input username={}", request.getUsername());
        log.info("로그인 input password={}", request.getPassword());

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Optional<User> optionalUser = userService.getUser(request.getUsername(), request.getPassword());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }

        return optionalUser.get();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (!Objects.isNull(session)) {
            session.removeAttribute(SESSION);
            session.removeAttribute(USERNAME);
        }
        return "redirect:/";
    }
}
