package com.nhnacademy.jdbc.board.like.web;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{boardId}/{username}")
    public String like(@PathVariable("boardId") long boardId,
                       @PathVariable("username") String username) {
        log.info("Like 등록 boardId={}", boardId);
        log.info("Like 등록 username={}", username);
        likeService.like(boardId, username);
        return "redirect:/";
    }

    @DeleteMapping("/{boardId}/{username}")
    public String unlike(@PathVariable long boardId,
                         @PathVariable("username") String username) {
        log.info("Unlike boardId={}", boardId);
        log.info("Unlike username={}", username);
        likeService.unlike(boardId, username);
        return "redirect:/";
    }

    @GetMapping("/{username}")
    public String findAllBoards(@PathVariable String username, ModelMap modelMap) {
        log.info("좋아요 목록 조회 username={}", username);
        List<BoardResponseDto> boards = likeService.findByUsername(username);
        modelMap.addAttribute("boards", boards);
        return "myBoards";
    }
}
