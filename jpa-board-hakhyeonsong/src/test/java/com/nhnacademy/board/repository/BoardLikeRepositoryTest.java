package com.nhnacademy.board.repository;

import com.nhnacademy.board.config.RootConfig;
import com.nhnacademy.board.config.WebConfig;
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
    @DisplayName("User PK와 Board PK에 해당하는 좋아요를 불러온다.")
    void findByUser_UserIdAndBoard_BoardId_success() throws Exception {
        //given
        int userId = 1;
        int boardId = 1;

        //when
        BoardLike boardLike = boardLikeRepository.findByUser_UserIdAndBoard_BoardId(userId, boardId);

        //then
        assertThat(boardLike.getUser().getUserId()).isEqualTo(userId);
        assertThat(boardLike.getBoard().getBoardId()).isEqualTo(boardId);
    }
}