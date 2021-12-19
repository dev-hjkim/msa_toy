//package com.fashion.celebrity.auth.jpa.controller;
//
//import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import static com.fashion.celebrity.auth.config.RestDocConfiguration.getDocumentRequest;
//import static com.fashion.celebrity.auth.config.RestDocConfiguration.getDocumentResponse;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@Transactional
//public class JpaAuthControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void validateMailTest() throws Exception {
//        // given
//        ValidateMailRequestDto requestDto = ValidateMailRequestDto.builder()
//                .userId("guswlsapdlf@naver.com")
//                .build();
//
//        String url = "http://192.168.1.16:8000/v1/auth/jpa/validate/mail";
//
//        // when
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        ResultActions result = this.mockMvc.perform(
//                post(url)
//                        .content(objectMapper.writeValueAsString(requestDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        // then
//        result.andExpect(status().isOk())
//                .andDo(document("ValidateMail",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("userId").type(JsonFieldType.STRING).description("이메일 아이디")
//                        ),
//                        responseFields(
//                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("api 성공 여부"),
//                                fieldWithPath("code").type(JsonFieldType.STRING).description("api 응답 코드"),
//                                fieldWithPath("message").type(JsonFieldType.STRING).description("api 응답 메시지")
//                        )
//                ));
//    }
//}
