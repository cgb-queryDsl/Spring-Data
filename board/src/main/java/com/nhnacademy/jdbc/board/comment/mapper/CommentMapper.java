package com.nhnacademy.jdbc.board.comment.mapper;

import com.nhnacademy.jdbc.board.comment.domain.Comment;
import com.nhnacademy.jdbc.board.comment.dto.CommentResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<CommentResponseDto> selectComments(@Param("boardId") Long boardId);
    Long insertComment(Comment comment);
    void updateComment(Comment comment);
    void deleteById(long commentId);

    long getBoardId(@Param("commentId") long commentId);
}
