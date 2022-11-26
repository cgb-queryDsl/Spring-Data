package com.nhnacademy.jdbc.board.board.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private long boardId;
    private String title;
    private String content;
    private String username;
    private String editorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCount;
    private List<String> files;
}
