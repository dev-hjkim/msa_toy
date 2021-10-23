package com.fashion.celebrity.auth.jpa.controller;

import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
import com.fashion.celebrity.auth.jpa.service.JpaAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JpaAuthController {
    private final JpaAuthService jpaAuthService;

    @PostMapping("/jpa/validate/mail")
    public boolean validateMail(@RequestBody ValidateMailRequestDto requestDto) {
        return jpaAuthService.validateMail(requestDto);
    }
}
