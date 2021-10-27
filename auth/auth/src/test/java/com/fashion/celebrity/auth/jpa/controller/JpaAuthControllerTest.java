package com.fashion.celebrity.auth.jpa.controller;

import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
import com.fashion.celebrity.auth.jpa.domain.auth.JpaAuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

        String url = "http://192.168.1.16:"+port+"/jpa/validate/mail";

        // when
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(url, requestDto, Boolean.class);
        try {
            ResultActions result = this.mockMvc.perform(
                    post(url)
                            .content(objectMapper.writeValueAsString(requestDto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(true);
    }
}
