package com.nhnacademy.jdbc.board.board.mapper;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    Optional<BoardResponseDto> selectBoard(@Param("id") long id);
    List<BoardResponseDto> selectBoards(@Param("offset") long offset, @Param("title") String title);
    Integer insertBoard(Board board);
    void updateBoard(Board board);
    void deleteById(long id);
    void restoreById(long id);
    long getTotalCount(@Param("title") String title);
    void updateCommentCount(@Param("boardId") long boardId, @Param("amount") int amount);
}
