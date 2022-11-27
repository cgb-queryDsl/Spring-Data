package com.nhnacademy.jdbc.board.board.web;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.board.service.BoardService;
import com.nhnacademy.jdbc.board.board.service.impl.DefaultBoardService;
import com.nhnacademy.jdbc.board.config.WebControllerAdvice;
import com.nhnacademy.jdbc.board.exception.BoardNotFoundException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class BoardControllerTest {

    private MockMvc mockMvc;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService = mock(DefaultBoardService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new BoardController(boardService))
                .setControllerAdvice(WebControllerAdvice.class)
                .build();
    }

    @Test
    @DisplayName("게시글 등록 페이지를 반환한다.")
    void boardForm() throws Exception {
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andExpect(view().name("boardRegister"));
    }

    @Test
    @DisplayName("게시글 등록 시 내용들에 대해 유효성 검증 실패 시 예외를 반환한다.")
    void boardRegister_fail_throwValidationFailedException() throws Exception {
        String inputTitle = "";
        String inputContent = "";
        String inputWriter = "";

        mockMvc.perform(post("/boards")
                        .param("title", inputTitle)
                        .param("content", inputContent)
                        .param("writer", inputWriter))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationFailedException));
    }

    @Test
    @DisplayName("게시글 등록 시 파일을 첨부하지 않은 경우")
    void boardRegister_success_withoutFiles() throws Exception {
        String inputTitle = "title";
        String inputContent = "content";
        String inputWriter = "Ramos";

        MockMultipartFile files = new MockMultipartFile("imageFiles", "", "", "".getBytes());

        mockMvc.perform(multipart("/boards")
                        .file(files)
                        .param("title", inputTitle)
                        .param("content", inputContent)
                        .param("writer", inputWriter))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("게시글 등록 시 파일을 첨부한 경우")
    void boardRegister_success_withFiles() throws Exception {
        String inputTitle = "title";
        String inputContent = "content";
        String inputWriter = "Ramos";

        MockMultipartFile files = new MockMultipartFile("imageFiles", "imagefile.png", "image/png", "<<png data>>".getBytes());

        mockMvc.perform(multipart("/boards")
                        .file(files)
                        .param("title", inputTitle)
                        .param("content", inputContent)
                        .param("writer", inputWriter))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("해당하는 게시글이 없는 경우 상세 페이지 반환에 실패하며 예외를 발생시킨다.")
    void boardView_fail_throwBoardNotFoundException() throws Exception {
        long boardId = 1L;

        when(boardService.findById(boardId)).thenThrow(BoardNotFoundException.class);

        mockMvc.perform(get("/boards/{boardId}", boardId))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BoardNotFoundException));
    }

    @Test
    @DisplayName("게시글 상세 페이지를 반환한다.")
    void boardView_success() throws Exception {
        long boardId = 1L;
        BoardResponseDto mockBoard = new BoardResponseDto();

        when(boardService.findById(boardId)).thenReturn(mockBoard);

        mockMvc.perform(get("/boards/{boardId}", boardId))
                .andExpect(status().isOk())
                .andExpect(view().name("boardView"));
    }

    @Test
    @DisplayName("등록되지 않은 게시글일 경우 수정 페이지 반환에 실패하며 예외를 발생시킨다.")
    void boardEditForm_fail_throwBoardNotFoundException() throws Exception {
        long boardId = 1L;

        when(boardService.findById(boardId)).thenThrow(BoardNotFoundException.class);

        mockMvc.perform(get("/boards/{boardId}/edit", boardId))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BoardNotFoundException));
    }

    @Test
    @DisplayName("게시글 수정 페이지를 반환한다.")
    void boardEditForm() throws Exception {
        long boardId = 1L;

        mockMvc.perform(get("/boards/{boardId}/edit", boardId))
                .andExpect(status().isOk())
                .andExpect(view().name("boardEditForm"));
    }

    @Test
    @DisplayName("게시글 수정시 내용들에 대해 유효성 검증 실패 시 예외를 반환한다.")
    void editBoard_fail_throwValidationFailedException() throws Exception {
        long boardId = 1L;
        String inputTitle = "";
        String inputContent = "";
        String inputEditorName = "";

        mockMvc.perform(put("/boards/{boardId}/edit", boardId)
                        .param("title", inputTitle)
                        .param("content", inputContent)
                        .param("editorName", inputEditorName))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationFailedException));
    }

    @Test
    @DisplayName("게시글 수정 성공")
    void editBoard_success() throws Exception {
        long boardId = 1L;
        String inputTitle = "title";
        String inputContent = "content";
        String inputEditorName = "user";

        mockMvc.perform(put("/boards/{boardId}/edit", boardId)
                        .param("title", inputTitle)
                        .param("content", inputContent)
                        .param("editorName", inputEditorName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

    }

    @Test
    @DisplayName("게시글 삭제 성공")
    void deleteBoard_success() throws Exception {
        long boardId = 1L;

        mockMvc.perform(delete("/boards/{boardId}", boardId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 복구 성공")
    void restoreBoard_success() throws Exception {
        long boardId = 1L;

        mockMvc.perform(put("/boards/{boardId}/restore", boardId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andDo(print());
    }
}