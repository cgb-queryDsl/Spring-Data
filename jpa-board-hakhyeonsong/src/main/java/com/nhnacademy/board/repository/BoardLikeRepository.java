package com.nhnacademy.board.repository;

import com.nhnacademy.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLike.Pk> {
    BoardLike findByUser_UserIdAndBoard_BoardId(Integer userId, Integer boardId);
}
