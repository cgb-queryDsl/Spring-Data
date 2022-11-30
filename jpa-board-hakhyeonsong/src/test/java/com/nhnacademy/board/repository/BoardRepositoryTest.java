package com.nhnacademy.board.repository;

import com.nhnacademy.board.config.RootConfig;
import com.nhnacademy.board.config.WebConfig;
import com.nhnacademy.board.entity.Board;
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
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("댓글 등록 시 해당 게시글의 댓글 개수가 증가한다.")
    void updateBoard_commentCount() throws Exception {
        //given
        int boardId = 1;
        int commentUpdateAmount = 1;

        //when
        boardRepository.updateBoard(boardId, commentUpdateAmount);

        //then
        Board board = boardRepository.findById(boardId).get();

        assertThat(board.getCommentCount()).isEqualTo(commentUpdateAmount);
    }

    @Test
    @DisplayName("게시글 제목으로 검색 성공")
    void findAllByTitleLike_success() throws Exception {
        //given
        String title = "게시";

        //when
        List<Board> boards = boardRepository.findAllByTitleLike(title);

        //then
        Board board = boards.get(0);

        assertThat(board.getTitle()).isEqualTo("게시글1");
        assertThat(board.getContent()).isEqualTo("게시글 내용");
        assertThat(board.getCommentCount()).isZero();
        assertThat(board.getEditorName()).isEqualTo("admin");
    }
}