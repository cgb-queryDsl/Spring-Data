package com.nhnacademy.jdbc.board.comment.service.impl;

import com.nhnacademy.jdbc.board.board.mapper.BoardMapper;
import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.dto.CommentRequest;
import com.nhnacademy.jdbc.board.comment.dto.CommentResponseDto;
import com.nhnacademy.jdbc.board.comment.mapper.CommentMapper;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentMapper commentMapper;
    private final BoardMapper boardMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(long boardId) {
        return commentMapper.selectComments(boardId);
    }

    @Override
    public Long register(Long boardId, CommentRequest request) {
        Comment comment = new Comment(Integer.parseInt(boardId.toString()), request.getCommenter(), request.getText());
        boardMapper.updateCommentCount(boardId, 1);
        return commentMapper.insertComment(comment);
    }

    @Override
    public void edit(long commentId, CommentRequest request) {
        Comment comment = new Comment(commentId, request.getCommenter(), request.getText());
        commentMapper.updateComment(comment);
    }

    @Override
    public void delete(long commentId) {
        long boardId = commentMapper.getBoardId(commentId);
        boardMapper.updateCommentCount(boardId, -1);
        commentMapper.deleteById(commentId);
    }
}
