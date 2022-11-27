package com.nhnacademy.jdbc.board.comment.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.jdbc.board.config.WebControllerAdvice;
import com.nhnacademy.jdbc.board.comment.dto.CommentRequest;
import com.nhnacademy.jdbc.board.comment.dto.CommentResponseDto;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.comment.service.impl.DefaultCommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = mock(DefaultCommentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CommentController(commentService))
                .setControllerAdvice(WebControllerAdvice.class)
                .build();

        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("게시글에 달린 댓글 목록 전체를 불러온다.")
    void getComments_success() throws Exception {
        long boardId = 1L;
        CommentResponseDto comment = new CommentResponseDto();
        List<CommentResponseDto> comments = List.of(comment);

        when(commentService.findAll(boardId)).thenReturn(comments);

        mockMvc.perform(get("/comments/all/{boardId}", boardId)
                .accept("application/json"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("유효성 검증으로 인한 댓글 등록 실패")
    void commentRegister_fail_returnHttpStatus_BAD_REQUEST() throws Exception {
        long boardId = 1L;
        CommentRequest request = new CommentRequest();

        mockMvc.perform(post("/comments/{boardId}", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("댓글 등록 성공")
    void commentRegister_success() throws Exception {
        long boardId = 1L;
        long commentId = 1L;
        CommentRequest request = new CommentRequest("comment text", "testUser");

        when(commentService.register(boardId, request)).thenReturn(commentId);

        mockMvc.perform(post("/comments/{boardId}", boardId)
                        .accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유효성 검증으로 인한 댓글 수정 실패")
    void commentEdit_fail_returnHttpStatus_BAD_REQUEST() throws Exception {
        long commentId = 1L;
        CommentRequest request = new CommentRequest();

        mockMvc.perform(put("/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void commentEdit_success() throws Exception {
        long commentId = 1L;
        CommentRequest request = new CommentRequest("comment text", "testUser");

        mockMvc.perform(put("/comments/{commentId}", commentId)
                        .accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글에 달린 댓글 삭제 성공")
    void commentDelete_success() throws Exception {
        long commentId = 1L;

        mockMvc.perform(delete("/comments/{commentId}", commentId)
                        .accept("application/json"))
                .andExpect(status().isOk());
    }
}