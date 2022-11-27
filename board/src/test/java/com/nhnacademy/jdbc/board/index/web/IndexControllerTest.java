package com.nhnacademy.jdbc.board.index.web;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.board.service.BoardService;
import com.nhnacademy.jdbc.board.board.service.impl.DefaultBoardService;
import com.nhnacademy.jdbc.board.util.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    private MockMvc mockMvc;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService = mock(DefaultBoardService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new IndexController(boardService))
                .build();
    }

    @Test
    @DisplayName("page 번호와 Title 검색 값을 QueryParameter로 주었을 경우 이에 대한 목록이 반환된다.")
    void indexPage_WithInputPageNumAndTitleQuery_success() throws Exception {
        long pageNum = 1;
        String inputTitleQuery = "test";
        ArrayList<String> files = new ArrayList<>();
        BoardResponseDto board = new BoardResponseDto(1L, "test", "contentTest", "user1", "user1", LocalDateTime.now(), LocalDateTime.now(), 0, files);
        Page<BoardResponseDto> boards = new Page<>(List.of(board), 1L);

        when(boardService.findAll(pageNum, inputTitleQuery)).thenReturn(boards);

        mockMvc.perform(get("/")
                        .param("page", "1")
                        .param("title", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("index/index"))
                .andDo(print());
    }

    @Test
    @DisplayName("page 번호와 Title 검색 값을 QueryParameter로 주지 않았을 경우, 전체 목록이 반환된다.")
    void indexPage_WithOutInputPageNumAndTitleQuery_success() throws Exception {
        long defaultPageNum = 1;
        String inputTitleQuery = "";
        ArrayList<String> files = new ArrayList<>();
        BoardResponseDto board = new BoardResponseDto(1L, "test", "contentTest", "user1", "user1", LocalDateTime.now(), LocalDateTime.now(), 0, files);
        Page<BoardResponseDto> boards = new Page<>(List.of(board), 1L);

        when(boardService.findAll(defaultPageNum, inputTitleQuery)).thenReturn(boards);

        mockMvc.perform(get("/")
                        .param("page", "1")
                        .param("title", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("index/index"))
                .andDo(print());
    }
}