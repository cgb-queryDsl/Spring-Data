package com.nhnacademy.jdbc.board.like.service.impl;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.exception.UserNotFoundException;
import com.nhnacademy.jdbc.board.like.mapper.LikeMapper;
import com.nhnacademy.jdbc.board.like.service.LikeService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultLikeService implements LikeService {

    private final LikeMapper likeMapper;
    private final UserMapper userMapper;

    @Override
    public List<BoardResponseDto> findByUsername(String username) {
        long userId = getUserId(username);
        return likeMapper.findAll(userId);
    }

    @Override
    public void like(long boardId, String username) {
        long userId = getUserId(username);
        likeMapper.like(userId, boardId);
    }

    @Override
    public void unlike(long boardId, String username) {
        long userId = getUserId(username);
        likeMapper.unlike(userId, boardId);
    }

    private long getUserId(String username) {
        Optional<User> optionalUser = userMapper.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("user not found");
        }
        return optionalUser.get().getId();
    }
}
