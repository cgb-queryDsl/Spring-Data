package com.nhnacademy.certificate.repository.certificate;

import com.nhnacademy.certificate.config.RootConfig;
import com.nhnacademy.certificate.config.WebConfig;
import com.nhnacademy.certificate.dto.web.CertificateIssueResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
class CertificateIssueRepositoryTest {

    @Autowired
    CertificateIssueRepository certificateIssueRepository;

    @Test
    @DisplayName("주민일련번호 기준으로 증명서 발급 내역을 불러오며, 한 페이지당 1개씩 불러온다.")
    void findAllByResident_success() throws Exception {
        //given
        PageRequest pageable = PageRequest.of(0, 1);

        //when
        List<CertificateIssueResponse> issues = certificateIssueRepository.findAllByResident(4L, pageable).getContent();

        //then
        assertThat(issues).hasSize(1);
        CertificateIssueResponse issueResponse = issues.get(0);

        assertThat(issueResponse.getName()).isEqualTo("남기준");
        assertThat(issueResponse.getResidentRegistrationNumber()).isEqualTo("790510-1234564");
        assertThat(issueResponse.getCertificateTypeCode()).isEqualTo("가족관계증명서");
    }
}