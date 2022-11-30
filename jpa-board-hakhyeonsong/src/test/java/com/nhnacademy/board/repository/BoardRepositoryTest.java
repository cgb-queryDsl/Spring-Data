package com.nhnacademy.board.repository;

import com.nhnacademy.board.config.RootConfig;
import com.nhnacademy.board.config.WebConfig;
import com.nhnacademy.board.dto.BoardDetailResponse;
import com.nhnacademy.board.dto.BoardResponse;
import com.nhnacademy.board.entity.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        String titleQuery = "게시";
        String username = "admin";
        String title = "게시글1";
        String content = "게시글 내용";

        //when
        List<BoardResponse> boards = boardRepository.findAllByTitleLike(titleQuery);

        //then
        BoardResponse board = boards.get(0);

        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getCommentCount()).isZero();
        assertThat(board.getEditorName()).isEqualTo(username);
    }

    @Test
    @DisplayName("1번 게시글을 삭제하면, deleted 필드가 true로 바뀐다.")
    void deleteBoard_success() throws Exception {
        //given

        //when
        boardRepository.deleteBoard(1);

        Board board = boardRepository.findById(1).get();

        //then
        assertThat(board.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("1번 게시글을 복원하면, deleted 필드가 false로 바뀐다.")
    void restoreBoard_success() throws Exception {
        //given

        //when
        boardRepository.restoreBoard(1);

        Board board = boardRepository.findById(1).get();

        //then
        assertThat(board.isDeleted()).isFalse();
    }

    @Test
    @DisplayName("게시판 전체 목록을 페이징과 함께 반환한다.")
    void pagination() throws Exception {
        //given
        String username = "admin";
        String title = "게시글1";
        String content = "게시글 내용";

        PageRequest pageable = PageRequest.of(0, 20);

        //when
        List<BoardResponse> boards = boardRepository.getAll(pageable).getContent();

        //then
        BoardResponse board = boards.get(0);

        assertThat(board.getUsername()).isEqualTo(username);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getCommentCount()).isZero();
        assertThat(board.getEditorName()).isEqualTo(username);
    }

    @Test
    @DisplayName("게시글의 상세 정보를 연관관계에 있는 엔티티와 함께 DTO로 반환한다.")
    void getBoardWithAssociation() throws Exception {
        //given
        int boardId = 1;
        String username = "admin";
        String title = "게시글1";
        String content = "게시글 내용";

        //when
        BoardDetailResponse board = boardRepository.getBoardWithAssociation(boardId);

        //then
        assertThat(board.getUsername()).isEqualTo(username);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getContent()).isEqualTo(content);
        assertThat(board.getCommentCount()).isZero();
        assertThat(board.getEditorName()).isEqualTo(username);
    }
}