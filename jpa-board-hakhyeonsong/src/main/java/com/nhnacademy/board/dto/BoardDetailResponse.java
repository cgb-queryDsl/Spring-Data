package com.nhnacademy.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class BoardDetailResponse {
    private Integer boardId;
    private String title;
    private String content;
    private String username;
    private String editorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCount;
    private List<String> files;

    @QueryProjection
    public BoardDetailResponse(Integer boardId, String title, String content, String username, String editorName, LocalDateTime createdAt, LocalDateTime updatedAt, int commentCount) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.username = username;
        this.editorName = editorName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentCount = commentCount;
    }
}
