package com.nhnacademy.jdbc.board.board.web;

import com.nhnacademy.jdbc.board.board.dto.BoardEditRequest;
import com.nhnacademy.jdbc.board.board.dto.BoardRegisterRequest;
import com.nhnacademy.jdbc.board.board.dto.BoardResponseDto;
import com.nhnacademy.jdbc.board.board.service.BoardService;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String boardForm() {
        return "boardRegister";
    }

    @PostMapping
    public String boardRegister(@Valid @ModelAttribute BoardRegisterRequest request,
                                BindingResult bindingResult,
                                @Value("${upload.dir}") String uploadDir) throws IOException {
        log.info("BoardRegisterRequest input title={}", request.getTitle());
        log.info("BoardRegisterRequest input content={}", request.getContent());
        log.info("BoardRegisterRequest input writerName={}", request.getWriter());

        checkValidation(bindingResult);

        boardService.registerBoard(request, uploadDir);

        return "redirect:/";
    }

    @GetMapping("/{boardId}")
    public String boardView(@PathVariable long boardId, ModelMap modelMap) {
        BoardResponseDto board = boardService.findById(boardId);
        modelMap.addAttribute("board", board);
        log.info(board.toString());
        return "boardView";
    }

    @GetMapping("/{boardId}/edit")
    public String boardEditForm(@PathVariable long boardId, ModelMap modelMap) {
        BoardResponseDto board = boardService.findById(boardId);
        modelMap.addAttribute("board", board);

        return "boardEditForm";
    }

    @PutMapping("/{boardId}/edit")
    public String editBoard(@PathVariable long boardId, @Valid @ModelAttribute BoardEditRequest request, BindingResult bindingResult) {
        log.info("BoardEditRequest input boardId={}", boardId);
        log.info("BoardEditRequest input title={}", request.getTitle());
        log.info("BoardEditRequest input content={}", request.getContent());
        log.info("BoardEditRequest input editorName={}", request.getEditorName());

        checkValidation(bindingResult);

        boardService.edit(boardId, request);
        return "redirect:/";
    }

    private void checkValidation(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable long boardId) {
        boardService.delete(boardId);
        return "redirect:/";
    }

    @PutMapping("/{boardId}/restore")
    public String restoreBoard(@PathVariable long boardId) {
        boardService.restore(boardId);
        return "redirect:/";
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename) throws MalformedURLException {
        String uploadDir = "/Users/hakhyeonsong/testupload/";
        UrlResource resource = new UrlResource("file:" + uploadDir + filename);

        String encodedUploadFileName = UriUtils.encode(filename, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
