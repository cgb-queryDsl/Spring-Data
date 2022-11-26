package com.nhnacademy.jdbc.board.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardEditRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String editorName;
}
