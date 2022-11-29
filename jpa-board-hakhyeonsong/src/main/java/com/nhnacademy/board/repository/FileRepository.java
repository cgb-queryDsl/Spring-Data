package com.nhnacademy.board.repository;

import com.nhnacademy.board.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAllByBoard_BoardId(Integer boardId);
}
