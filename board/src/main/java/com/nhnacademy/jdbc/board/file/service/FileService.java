package com.nhnacademy.jdbc.board.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void uploadFiles(long boardId, List<MultipartFile> uploadFiles, String uploadDir) throws IOException;
    List<String> downloadFiles(long boardId);
}
