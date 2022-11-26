package com.nhnacademy.jdbc.board.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private long commentId;
    private String commenter;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long boardId;
}
