package com.nhnacademy.jdbc.board.file.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {
    List<String> selectFiles(long boardId);
    void insertFile(@Param("boardId") long boardId, @Param("fileNames") List<String> fileNames);
}
