package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.util.Page;
import com.nhnacademy.jdbc.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final BoardService boardService;

    @GetMapping("/")
    public String index(@RequestParam(value = "page", required = false) Long page,
                        @RequestParam(value = "title", required = false) String title,
                        ModelMap modelMap) {
        long pageNum = getPageNum(page);

        String inputTitleQuery = getInputTitleQuery(title);

        Page<BoardResponseDto> boards = boardService.findAll(pageNum, inputTitleQuery);
        int totalPages = getTotalPages(boards);

        modelMap.addAttribute("boards", boards);
        modelMap.addAttribute("totalPages", totalPages);

        return "index/index";
    }

    private int getTotalPages(Page<BoardResponseDto> boards) {
        long totalCount = boards.getTotalCount();
        int pageSize = 20;
        return (totalCount == 0) ? 1 : (int) Math.ceil((double) totalCount / (double) pageSize);
    }

    private String getInputTitleQuery(String title) {
        String query = "";
        if (!Objects.isNull(title)) {
            log.info("InputTitleQueryParameter={}", title);
            query = title;
        }
        return query;
    }

    private long getPageNum(Long page) {
        long pageNum = 1;
        if (!Objects.isNull(page) && page >= 1) {
            log.info("InputPageQueryParameter={}", page);
            pageNum = page;
        }
        return pageNum;
    }
}
