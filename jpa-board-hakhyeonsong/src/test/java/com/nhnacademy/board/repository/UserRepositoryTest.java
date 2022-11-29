package com.nhnacademy.board.repository;

import com.nhnacademy.board.config.RootConfig;
import com.nhnacademy.board.config.WebConfig;
import com.nhnacademy.board.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.nhnacademy.board.UserRole.ROLE_ADMIN;
import static com.nhnacademy.board.UserRole.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사전에 insert 해둔 user들을 불러온다.")
    void findAll() {
        //when
        List<User> all = userRepository.findAll();

        //then
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("User Insert에 성공한다.")
    void insertQueryTest() throws Exception {
        //given
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("12345");
        user.setUserRole(ROLE_USER.name());
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);


        //when
        User findUser = userRepository.findByUsername("testuser");

        //then
        assertThat(findUser).isNotNull();
        assertThat(findUser.getUsername()).isEqualTo("testuser");
        assertThat(findUser.getPassword()).isEqualTo("12345");
        assertThat(findUser.getUserRole()).isEqualTo(ROLE_USER.name());
    }

    @Test
    @DisplayName("username과 password로 User를 불러온다.")
    void findByUsernameAndPassword_success() throws Exception {

        //when
        User user = userRepository.findByUsernameAndPassword("admin", "12345");

        //then
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getPassword()).isEqualTo("12345");
        assertThat(user.getUserRole()).isEqualTo(ROLE_ADMIN.name());
    }
}