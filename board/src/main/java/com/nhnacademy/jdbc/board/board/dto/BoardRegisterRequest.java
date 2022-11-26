package com.nhnacademy.jdbc.board.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardRegisterRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String writer;

    private List<MultipartFile> uploadFiles;
}
