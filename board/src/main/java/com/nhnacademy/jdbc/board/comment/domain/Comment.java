package com.nhnacademy.jdbc.board.comment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Comment {
    @Setter
    private long commentId;
    private int boardId;
    private String commenter;
    private String text;

    public Comment(int boardId, String commenter, String text) {
        this.boardId = boardId;
        this.commenter = commenter;
        this.text = text;
    }

    public Comment(long commentId, String commenter, String text) {
        this.commentId = commentId;
        this.commenter = commenter;
        this.text = text;
    }
}
