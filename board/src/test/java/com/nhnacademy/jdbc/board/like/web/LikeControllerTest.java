package com.nhnacademy.jdbc.board.like.web;

import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.exception.UserNotFoundException;
import com.nhnacademy.jdbc.board.like.service.LikeService;
import com.nhnacademy.jdbc.board.like.service.impl.DefaultLikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LikeControllerTest {

    private MockMvc mockMvc;

    private LikeService likeService;

    @BeforeEach
    void setUp() {
        likeService = mock(DefaultLikeService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LikeController(likeService))
                .build();
    }

    @Test
    @DisplayName("user가 없는 경우 UserNotFoundException 예외를 발생시키며 좋아요 등록에 실패한다.")
    void like_fail_throwUserNotFoundException() throws Exception {
        long boardId = 1L;
        String invalidUsername = "test";

        doThrow(UserNotFoundException.class).when(likeService).like(boardId, invalidUsername);

        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/likes/{boardId}/{username}", boardId, invalidUsername))
                        .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("좋아요 등록에 성공한다.")
    void like_success() throws Exception {
        long boardId = 1L;
        String username = "test";

        mockMvc.perform(post("/likes/{boardId}/{username}", boardId, username))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("user가 없는 경우 UserNotFoundException 예외를 발생시키며 좋아요 취소에 실패한다.")
    void unlike_fail_throwUserNotFoundException() throws Exception {
        long boardId = 1L;
        String invalidUsername = "test";

        doThrow(UserNotFoundException.class).when(likeService).unlike(boardId, invalidUsername);

        Throwable th = catchThrowable(() ->
                mockMvc.perform(delete("/likes/{boardId}/{username}", boardId, invalidUsername))
                        .andDo(print()));

        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("좋아요 취소에 성공한다.")
    void unlike_success() throws Exception {
        long boardId = 1L;
        String username = "test";

        mockMvc.perform(delete("/likes/{boardId}/{username}", boardId, username))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @DisplayName("사용자가 좋아한 게시글 목록 전체를 반환한다.")
    void findAllBoards() throws Exception {
        String username = "test";

        ArrayList<String> files = new ArrayList<>();
        BoardResponseDto board = new BoardResponseDto(1L, "test", "contentTest", "user1", "user1", LocalDateTime.now(), LocalDateTime.now(), 0, files);
        List<BoardResponseDto> boards = List.of(board);

        when(likeService.findByUsername(username)).thenReturn(boards);

        mockMvc.perform(get("/likes/{username}", username))
                .andExpect(status().isOk())
                .andExpect(view().name("myBoards"));
    }
}