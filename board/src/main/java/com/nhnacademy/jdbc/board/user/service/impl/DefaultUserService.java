package com.nhnacademy.jdbc.board.user.service.impl;

import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserMapper userMapper;

    @Override
    public Optional<User> getUser(String username, String password) {
        return userMapper.selectUser(username, password);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
