package com.nhnacademy.jdbc.board.board.service.impl;

import com.nhnacademy.jdbc.board.board.domain.*;
import com.nhnacademy.jdbc.board.board.dto.BoardEditRequest;
import com.nhnacademy.jdbc.board.board.dto.BoardRegisterRequest;
import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.board.mapper.BoardMapper;
import com.nhnacademy.jdbc.board.board.service.BoardService;
import com.nhnacademy.jdbc.board.file.service.FileService;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.service.UserService;
import com.nhnacademy.jdbc.board.util.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultBoardService implements BoardService {

    private final BoardMapper boardMapper;
    private final UserService userService;
    private final FileService fileService;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponseDto> findAll(long index, String title) {
        long totalCount = boardMapper.getTotalCount(title);
        List<BoardResponseDto> boards = boardMapper.selectBoards((index - 1) * 20, title);

        return new Page<>(boards, totalCount);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseDto findById(long boardId) {
        Optional<BoardResponseDto> boardDto = boardMapper.selectBoard(boardId);

        if (boardDto.isEmpty()) {
            throw new RuntimeException("board not found");
        }

        List<String> downloadFiles = fileService.downloadFiles(boardId);
        log.info(downloadFiles.toString());

        BoardResponseDto board = boardDto.get();
        board.setFiles(downloadFiles);

        return board;
    }

    @Override
    public void registerBoard(BoardRegisterRequest request, String uploadDir) throws IOException {
        Optional<User> optionalUser = userService.findByUsername(request.getWriter());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        User user = optionalUser.get();

        Board dto = new Board(user.getId(), request.getTitle(), request.getContent(), request.getWriter());
        Integer boardId = boardMapper.insertBoard(dto);
        log.info("boardId={}", boardId);

        fileService.uploadFiles(boardId, request.getUploadFiles(), uploadDir);
    }

    @Override
    public void edit(long boardId, BoardEditRequest request) {
        Board dto = new Board(boardId, request.getTitle(), request.getContent(), request.getEditorName());
        boardMapper.updateBoard(dto);
    }

    @Override
    public void delete(long boardId) {
        boardMapper.deleteById(boardId);
    }

    @Override
    public void restore(long boardId) {
        boardMapper.restoreById(boardId);
    }
}
