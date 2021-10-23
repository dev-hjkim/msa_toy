package com.fashion.celebrity.auth.jpa.controller;

import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
import com.fashion.celebrity.auth.jpa.domain.auth.JpaAuthRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaAuthControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JpaAuthRepository jpaAuthRepository;

    @After
    public void tearDown() {
        jpaAuthRepository.deleteAll();
    }

    @Test
    public void validateMailTest() {
        // given
        String userId = "guswlsapdlf@naver.com";

        ValidateMailRequestDto requestDto = ValidateMailRequestDto.builder()
                .userId(userId)
                .build();

        String url = "http://localhost:"+port+"/jpa/validate/mail";

        // when
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(url, requestDto, Boolean.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(true);
    }
}
