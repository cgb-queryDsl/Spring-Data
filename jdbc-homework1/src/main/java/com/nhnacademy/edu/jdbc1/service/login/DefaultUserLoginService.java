package com.nhnacademy.edu.jdbc1.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserLoginService implements UserLoginService {

    private final UserRepository userRepository;

    @Override
    public User login(LoginRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            throw new RuntimeException("user 없음");
        }

        return user.get();
    }
}
