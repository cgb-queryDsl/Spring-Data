package com.nhnacademy.jdbc.board.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {
    private long userId;
    private String title;
    private String content;
    private String editorName;
    @Setter
    private int boardId;

    public Board(long userId, String title, String content, String editorName) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.editorName = editorName;
    }
}
