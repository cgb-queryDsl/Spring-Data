package com.nhnacademy.board.repository;

import com.nhnacademy.board.config.RootConfig;
import com.nhnacademy.board.config.WebConfig;
import com.nhnacademy.board.entity.Board;
import com.nhnacademy.board.entity.BoardLike;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class BoardLikeRepositoryTest {

    @Autowired
    BoardLikeRepository boardLikeRepository;

    @Test
    @DisplayName("1번 유저가 좋아한 게시글 목록을 불러온다.")
    void findByUser_UserIdAndBoard_BoardId_success() throws Exception {
        //given
        int userId = 1;
        int boardId = 1;
        String username = "admin";
        String title = "게시글1";
        String content = "게시글 내용";

        //when
        List<BoardLike> likes = boardLikeRepository.findAllByUser_UserId(userId);

        BoardLike boardLike = likes.get(0);

        //then
        Board board = boardLike.getBoard();

        assertThat(likes).hasSize(1);
        assertThat(boardLike.getUser().getUserId()).isEqualTo(userId);
        assertThat(board.getBoardId()).isEqualTo(boardId);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getEditorName()).isEqualTo(username);
    }
}