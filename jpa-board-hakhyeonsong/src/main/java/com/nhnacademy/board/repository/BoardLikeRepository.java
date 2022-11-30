package com.nhnacademy.board.repository;

import com.nhnacademy.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLike.Pk> {
    @Query("select bl " +
            "from BoardLike bl " +
            "where bl.pk.userId = :userId ")
    List<BoardLike> findAllByUser_UserId(@Param("userId") Integer userId);
}