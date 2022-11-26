package com.nhnacademy.jdbc.board.board.service;

import com.nhnacademy.jdbc.board.board.dto.BoardEditRequest;
import com.nhnacademy.jdbc.board.board.dto.BoardRegisterRequest;
import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.util.Page;

import java.io.IOException;

public interface BoardService {
    Page<BoardResponseDto> findAll(long index, String title);
    BoardResponseDto findById(long boardId);
    void registerBoard(BoardRegisterRequest request, String uploadDir) throws IOException;
    void edit(long boardId, BoardEditRequest request);
    void delete(long boardId);
    void restore(long boardId);
}
