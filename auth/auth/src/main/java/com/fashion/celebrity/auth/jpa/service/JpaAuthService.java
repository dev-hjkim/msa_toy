package com.fashion.celebrity.auth.jpa.service;

import com.fashion.celebrity.auth.common.utils.GenerateCertCodeUtil;
import com.fashion.celebrity.auth.common.utils.SendCertMailUtil;
import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
import com.fashion.celebrity.auth.jpa.domain.auth.JpaAuthRepository;
import com.fashion.celebrity.auth.jpa.domain.auth.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class JpaAuthService {
    private final JpaAuthRepository jpaAuthRepository;

    @Autowired
    private JavaMailSender javaMailSender;


    @Transactional
    public boolean validateMail(ValidateMailRequestDto requestDto) {
        boolean sendMail;

        String userId = jpaAuthRepository.findByUserId(requestDto.getUserId());

        if (userId == null) {
            String certCode = GenerateCertCodeUtil.generate(10);

            // status 3으로(createMail) insert + certCode update(updateCertCode)
            jpaAuthRepository.save(Users.builder()
                            .userId(requestDto.getUserId())
                            .status("3")
                            .loginCnt(0)
                            .cnlCd("01")
                            .certNum(certCode)
                            .build());

            sendMail = SendCertMailUtil.sendMail(javaMailSender, requestDto.getUserId(), certCode);
        } else {
            sendMail = false;
        }
        return sendMail;
    }
}
