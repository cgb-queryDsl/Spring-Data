package com.nhnacademy.board.controller;

import com.nhnacademy.board.dto.LoginRequest;
import com.nhnacademy.board.entity.User;
import com.nhnacademy.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/user")
    public User findByUsernameAndPassword(@RequestBody LoginRequest request) {
        return userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
    }

}
