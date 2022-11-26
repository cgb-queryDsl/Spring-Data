package com.nhnacademy.jdbc.board.like.service;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;

import java.util.List;

public interface LikeService {
    List<BoardResponseDto> findByUsername(String username);
    void like(long boardId, String username);
    void unlike(long boardId, String username);
}
