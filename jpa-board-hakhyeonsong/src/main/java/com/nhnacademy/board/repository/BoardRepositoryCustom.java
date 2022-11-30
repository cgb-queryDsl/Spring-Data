package com.nhnacademy.board.repository;

import com.nhnacademy.board.dto.BoardDetailResponse;
import com.nhnacademy.board.dto.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BoardRepositoryCustom {
    Page<BoardResponse> getAll(Pageable pageable);
    BoardDetailResponse getBoardWithAssociation(Integer boardId);
    List<BoardResponse> findAllByTitleLike(String title);
}
