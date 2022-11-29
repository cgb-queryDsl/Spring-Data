package com.nhnacademy.board.repository;

import com.nhnacademy.board.config.RootConfig;
import com.nhnacademy.board.config.WebConfig;
import com.nhnacademy.board.entity.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
        @ContextConfiguration(classes = RootConfig.class),
        @ContextConfiguration(classes = WebConfig.class)
})
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;

    @Test
    @DisplayName("1번 게시글에 등록된 File들을 모두 불러온다.")
    void findAllByBoard_BoardId_success() throws Exception {
        //given
        int boardId = 1;

        //when
        List<File> files = fileRepository.findAllByBoard_BoardId(boardId);

        //then
        assertThat(files).hasSize(1);
        assertThat(files.get(0).getFilename()).isEqualTo("파일이름");
    }
}