package com.nhnacademy.jdbc.board.user.web;

import com.nhnacademy.jdbc.board.config.WebControllerAdvice;
import com.nhnacademy.jdbc.board.exception.UserNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.service.impl.DefaultUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.nhnacademy.jdbc.board.user.domain.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LoginControllerTest {

    private MockMvc mockMvc;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(DefaultUserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(userService))
                .setControllerAdvice(WebControllerAdvice.class)
                .build();
    }

    @Test
    @DisplayName("이미 로그인이 되어 있는 경우 메인 페이지를 반환한다.")
    void login_alreadyLogon() throws Exception {
        MockHttpSession mockHttpSession = new MockHttpSession();
        String session = "SESSION";
        String sessionValue = "sessionValue";
        String username = "USERNAME";
        String usernameValue = "test";

        mockHttpSession.setAttribute(session, sessionValue);
        mockHttpSession.setAttribute(username, usernameValue);

        mockMvc.perform(get("/login")
                        .session(mockHttpSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andDo(print());
    }

    @Test
    @DisplayName("로그인이 되어있지 않은 경우 loginForm 페이지를 반환한다.")
    void login_viewLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    @DisplayName("Id/Password가 맞지 않는 경우 login 실패")
    void doLogin_fail_notMatchedIdAndPassword_throwUserNotFoundException() throws Exception {
        String invalidUsername = "invalidId";
        String invalidPassword = "invalidPassword";

        Optional<User> optionalUser = Optional.empty();

        when(userService.getUser(invalidUsername, invalidPassword)).thenReturn(optionalUser);

        mockMvc.perform(post("/login")
                        .param("username", invalidUsername)
                        .param("password", invalidPassword))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException));
    }

    @Test
    @DisplayName("입력한 Username/Password에 대한 유효성 검증 실패 시 login 실패")
    void doLogin_fail_whenInputEmptyIdAndPassword_throwValidationFailedException() throws Exception {
        String invalidUsername = "";
        String invalidPassword = "";


        mockMvc.perform(post("/login")
                        .param("username", invalidUsername)
                        .param("password", invalidPassword))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationFailedException));
    }

    @Test
    @DisplayName("로그인 성공 시 홈으로 이동한다.")
    void doLogin_success() throws Exception {
        String inputUsername = "id";
        String inputPassword = "pwd";
        User user = new User(1L, inputUsername, inputPassword, ROLE_USER, LocalDateTime.now());

        Optional<User> optionalUser = Optional.of(user);

        when(userService.getUser(inputUsername, inputPassword)).thenReturn(optionalUser);

        mockMvc.perform(post("/login")
                        .param("username", inputUsername)
                        .param("password", inputPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("로그아웃 성공 시 index 페이지를 반환한다.")
    void logout() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/logout")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}