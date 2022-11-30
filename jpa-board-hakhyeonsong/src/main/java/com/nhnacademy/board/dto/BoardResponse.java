package com.nhnacademy.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class BoardResponse {
    private Integer boardId;
    private String title;
    private String content;
    private String username;
    private String editorName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int commentCount;
    private boolean deleted;

    @QueryProjection
    public BoardResponse(Integer boardId, String title, String content, String username, String editorName, LocalDateTime createdAt, LocalDateTime updatedAt, int commentCount, boolean deleted) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.username = username;
        this.editorName = editorName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentCount = commentCount;
        this.deleted = deleted;
    }
}
