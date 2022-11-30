package com.nhnacademy.board.repository;

import com.nhnacademy.board.dto.BoardDetailResponse;
import com.nhnacademy.board.dto.BoardResponse;
import com.nhnacademy.board.dto.QBoardDetailResponse;
import com.nhnacademy.board.dto.QBoardResponse;
import com.nhnacademy.board.entity.Board;
import com.nhnacademy.board.entity.QBoard;
import com.nhnacademy.board.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardRepositoryCustom {

    public BoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<BoardResponse> getAll(Pageable pageable) {
        QBoard board = QBoard.board;
        QUser user = QUser.user;

        List<BoardResponse> boards = from(board)
                .select(new QBoardResponse(
                        board.boardId,
                        board.title,
                        board.content,
                        user.username,
                        board.editorName,
                        board.createdAt,
                        board.createdAt,
                        board.commentCount,
                        board.deleted
                ))
                .innerJoin(board.user, user)
                .distinct()
                .where(board.deleted.eq(false))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetch();

        Long totalCount = from(board)
                .select(board.count())
                .fetchOne();

        return new PageImpl<>(boards, pageable, totalCount);
    }

    @Override
    public BoardDetailResponse getBoardWithAssociation(Integer boardId) {
        QBoard board = QBoard.board;
        QUser user = QUser.user;

        return from(board)
                .select(new QBoardDetailResponse(
                        board.boardId,
                        board.title,
                        board.content,
                        user.username,
                        board.editorName,
                        board.createdAt,
                        board.updatedAt,
                        board.commentCount
                ))
                .innerJoin(board.user, user)
                .where(board.boardId.eq(boardId))
                .fetchOne();
    }

    @Override
    public List<BoardResponse> findAllByTitleLike(String title) {
        QBoard board = QBoard.board;
        QUser user = QUser.user;

        return from(board)
                .select(new QBoardResponse(
                        board.boardId,
                        board.title,
                        board.content,
                        user.username,
                        board.editorName,
                        board.createdAt,
                        board.createdAt,
                        board.commentCount,
                        board.deleted
                ))
                .innerJoin(board.user, user)
                .where(board.title.like("%" + title + "%"))
                .fetch();
    }
}
