package com.fashion.celebrity.auth.jpa.controller;

import com.fashion.celebrity.auth.common.dto.ApiDto;
import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
import com.fashion.celebrity.auth.jpa.service.JpaAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JpaAuthController {
    private final JpaAuthService jpaAuthService;

    @PostMapping("/jpa/validate/mail")
    public ResponseEntity<Object> validateMail(@RequestBody ValidateMailRequestDto requestDto) {
        ApiDto apiDto = new ApiDto();
        String status = jpaAuthService.validateMail(requestDto);

        if (status.equals("01")) {
            apiDto.setSuccess(true);
            apiDto.setCode("");
            apiDto.setMessage("인증번호를 발송하였습니다.");
        } else if (status.equals("02")) {
            apiDto.setSuccess(false);
            apiDto.setCode("");
            apiDto.setMessage("인증번호 발송에 실패하였습니다. 담당자에게 문의해주세요.");
        } else {
            apiDto.setSuccess(false);
            apiDto.setCode("");
            apiDto.setMessage("이미 존재하는 아이디입니다.");
        }
        return new ResponseEntity<>(apiDto, HttpStatus.OK);
    }
}
