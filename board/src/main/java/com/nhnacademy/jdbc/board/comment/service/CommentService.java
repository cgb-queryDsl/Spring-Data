package com.nhnacademy.jdbc.board.comment.service;

import com.nhnacademy.jdbc.board.comment.dto.CommentRequest;
import com.nhnacademy.jdbc.board.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> findAll(long boardId);
    Long register(Long boardId, CommentRequest request);
    void edit(long commentId, CommentRequest request);
    void delete(long commentId);
}
