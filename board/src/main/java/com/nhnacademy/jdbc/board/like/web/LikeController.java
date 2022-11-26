package com.nhnacademy.jdbc.board.like.web;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{boardId}/{username}")
    public String like(@PathVariable("boardId") long boardId,
                       @PathVariable("username") String username) {
        likeService.like(boardId, username);
        return "redirect:/";
    }

    @DeleteMapping("/{boardId}/{username}")
    public String unlike(@PathVariable long boardId,
                         @PathVariable("username") String username) {
        likeService.unlike(boardId, username);
        return "redirect:/";
    }

    @GetMapping("/{username}")
    public String findAllBoards(@PathVariable String username, ModelMap modelMap) {
        List<BoardResponseDto> boards = likeService.findByUsername(username);
        modelMap.addAttribute("boards", boards);
        return "myBoards";
    }
}
