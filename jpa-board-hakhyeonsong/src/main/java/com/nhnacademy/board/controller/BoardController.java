package com.nhnacademy.board.controller;

import com.nhnacademy.board.dto.BoardDetailResponse;
import com.nhnacademy.board.dto.BoardResponse;
import com.nhnacademy.board.entity.File;
import com.nhnacademy.board.repository.BoardLikeRepository;
import com.nhnacademy.board.repository.BoardRepository;
import com.nhnacademy.board.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final BoardLikeRepository likeRepository;

    @GetMapping
    public List<BoardResponse> getAllBoards(Pageable pageable) {
        return boardRepository.getAll(pageable).getContent();
    }

    @GetMapping("/{boardId}")
    public BoardDetailResponse getDetailBoard(@PathVariable Integer boardId) {
        BoardDetailResponse board = boardRepository.getBoardWithAssociation(boardId);

        List<String> files = fileRepository.findAllByBoard_BoardId(boardId).stream()
                .map(File::getFilename)
                .collect(Collectors.toList());

        board.setFiles(files);

        return board;
    }

    @GetMapping("/query")
    public List<BoardResponse> findByTitle(@RequestParam(value = "title", required = false) String title) {
        return boardRepository.findAllByTitleLike(title);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity delete(@PathVariable Integer boardId) {
        boardRepository.deleteBoard(boardId);
        return ResponseEntity.ok().body("Board number #" + boardId + " delete success");
    }

    @PutMapping("/{boardId}/restore")
    public ResponseEntity restore(@PathVariable Integer boardId) {
        boardRepository.restoreBoard(boardId);
        return ResponseEntity.ok().body("Board number #" + boardId + " restore success");
    }
}
