package com.nhnacademy.jdbc.board.like.service.impl;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
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
        Optional<User> optionalUser = userMapper.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("유저 없음");
        }
        long userId = optionalUser.get().getId();
        return likeMapper.findAll(userId);
    }

    @Override
    public void like(long boardId, String username) {
        Optional<User> optionalUser = userMapper.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("유저 없음");
        }
        long userId = optionalUser.get().getId();
        likeMapper.like(userId, boardId);
    }

    @Override
    public void unlike(long boardId, String username) {
        Optional<User> optionalUser = userMapper.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("유저 없음");
        }
        long userId = optionalUser.get().getId();
        likeMapper.unlike(userId, boardId);
    }
}
