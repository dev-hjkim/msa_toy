package com.fashion.celebrity.auth.jpa.service;

import com.fashion.celebrity.auth.common.utils.GenerateCertCodeUtil;
import com.fashion.celebrity.auth.common.utils.SendCertMailUtil;
import com.fashion.celebrity.auth.jpa.controller.dto.ValidateMailRequestDto;
import com.fashion.celebrity.auth.jpa.domain.auth.JpaAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class JpaAuthService {
    private final JpaAuthRepository jpaAuthRepository;


    @Transactional
    public boolean validateMail(ValidateMailRequestDto requestDto) {
        boolean sendMail;

        String userId = jpaAuthRepository.findByUserId(requestDto.getUserId());

        if (userId.length() <= 0) {
            String certCode = GenerateCertCodeUtil.generate(10);

            // status 3으로(createMail) insert + certCode update(updateCertCode)
            jpaAuthRepository.saveCert(requestDto.getUserId(), certCode);

            sendMail = SendCertMailUtil.sendMail(requestDto.getUserId(), certCode);
        } else {
            sendMail = false;
        }
        return sendMail;
    }
}
