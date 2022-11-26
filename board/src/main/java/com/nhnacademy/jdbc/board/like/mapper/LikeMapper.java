package com.nhnacademy.jdbc.board.like.mapper;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {
    List<BoardResponseDto> findAll(long userId);
    void like(@Param("userId") long userId, @Param("boardId") long boardId);
    void unlike(@Param("userId") long userId, @Param("boardId") long boardId);
}
