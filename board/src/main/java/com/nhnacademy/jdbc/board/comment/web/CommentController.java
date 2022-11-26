package com.nhnacademy.jdbc.board.comment.web;

import com.nhnacademy.jdbc.board.comment.dto.CommentRequest;
import com.nhnacademy.jdbc.board.comment.dto.CommentResponseDto;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/all/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable long boardId) {
        List<CommentResponseDto> comments = commentService.findAll(boardId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<Object> commentRegister(@PathVariable Long boardId,
                                                @Valid @RequestBody CommentRequest request,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        log.info(request.getText());
        log.info(request.getCommenter());

        return ResponseEntity.ok(commentService.register(boardId, request));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Object> commentEdit(@PathVariable long commentId,
                                              @Valid @RequestBody CommentRequest request,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        commentService.edit(commentId, request);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Object> commentDelete(@PathVariable long commentId) {

        commentService.delete(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
