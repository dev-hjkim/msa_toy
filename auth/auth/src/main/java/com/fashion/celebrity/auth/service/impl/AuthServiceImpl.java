package com.fashion.celebrity.auth.service.impl;

import com.fashion.celebrity.auth.dto.*;
import com.fashion.celebrity.auth.mapper.AuthMapper;
import com.fashion.celebrity.auth.mapper.converter.AuthConverter;
import com.fashion.celebrity.auth.model.DupUserInfo;
import com.fashion.celebrity.auth.model.FindIdInfo;
import com.fashion.celebrity.auth.model.FindPwInfo;
import com.fashion.celebrity.auth.model.UserInfo;
import com.fashion.celebrity.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("AuthService")
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthMapper authMapper;

    @Autowired
    AuthConverter authConverter;

    @Autowired
    JavaMailSender javaMailSender;

    private static final String FROM_ADDRESS = "guswlsapdlf@gmail.com";
    private static final String RANDOM_SAMPLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklnmopqrstuvwxyz0123456789";

    @Override
    public UserDto selectAuthUser(String username) {
        UserInfo userInfo = this.authMapper.selectAuthUser(username);
        return this.authConverter.INSTANCE.convertAuthLogin(userInfo);
    }

    @Override
    public DupUserInfo selectDupMail(ValidateDtos.RequestEmail dto) {
        return this.authMapper.selectDupMail(dto);
    }

    @Override
    public int createEmail(String email) {
        return this.authMapper.createEmail(email);
    }

    @Override
    public boolean sendEmail(ValidateDtos.RequestEmail dto) {
        boolean sendMail;

        String certCode = "";

        for (int i=0; i<10; i++) {
            Random random = new Random();
            int randomWithNextInt = random.nextInt(RANDOM_SAMPLE.length());
            certCode += RANDOM_SAMPLE.charAt(randomWithNextInt);
        }

        System.out.println(certCode);
        dto.setCertCode(certCode);
        this.authMapper.updateCertCode(dto);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(dto.getEmail());
            message.setSubject("[Celebrity] 회원가입 인증 메일");
            message.setText("인증 번호는 [" + certCode + "] 입니다.");
            javaMailSender.send(message);

            sendMail = true;
        } catch (Exception ex) {
            sendMail = false;
        }

        return sendMail;
    }

    @Override
    public String selectCertCode(String email) {
        return this.authMapper.selectCertCode(email);
    }

    @Override
    public DupUserInfo selectNickname(String nickname) {
        return this.authMapper.selectNickname(nickname);
    }

    @Override
    public int createUser(SignupDtos.Request dto) {
        return this.authMapper.createUser(dto);
    }

    @Override
    public LoginDtos.Response selectUser(String email) {
        UserInfo userInfo = this.authMapper.selectUser(email);
        return this.authConverter.INSTANCE.convertLogin(userInfo);
    }

    @Override
    public int updateUserLogin(String email) {
        return this.authMapper.updateUserLogin(email);
    }

    @Override
    public int updateTrial(LoginDtos.Request dto) {
        return this.authMapper.updateTrial(dto);
    }

    @Override
    public int updateUserStatus(LoginDtos.Request dto) {
        return this.authMapper.updateUserStatus(dto);
    }

    @Override
    public int updateUserLogout(String email) {
        return this.authMapper.updateUserLogout(email);
    }

    @Override
    public FindDtos.ResponseIdPw findId(String phone) {
        FindIdInfo findIdInfo = this.authMapper.findId(phone);
        return this.authConverter.INSTANCE.convertFindId(findIdInfo);
    }

    @Override
    public FindDtos.ResponseIdPw findPw(FindDtos.RequestPw dto) {
        FindPwInfo findPwInfo = this.authMapper.findPw(dto);
        return this.authConverter.INSTANCE.convertFindPw(findPwInfo);
    }

    @Override
    public int modPw(ModifyDtos.RequestPw dto) {
        return this.authMapper.modPw(dto);
    }
}
