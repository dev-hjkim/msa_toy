package com.fashion.celebrity.auth.service.impl;

import com.fashion.celebrity.auth.dto.request.*;
import com.fashion.celebrity.auth.dto.response.ResFindIdDto;
import com.fashion.celebrity.auth.dto.response.ResFindPwDto;
import com.fashion.celebrity.auth.dto.response.ResLoginDto;
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
    public DupUserInfo selectDupMail(ReqDupMailDto dto) {
        return this.authMapper.selectDupMail(dto);
    }

    @Override
    public int createMail(String username) {
        return this.authMapper.createMail(username);
    }

    @Override
    public boolean sendEmail(ReqDupMailDto dto) {
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
            message.setTo(dto.getUsername());
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
    public String selectCertCode(String username) {
        return this.authMapper.selectCertCode(username);
    }

    @Override
    public DupUserInfo selectNickname(String nickname) {
        return this.authMapper.selectNickname(nickname);
    }

    @Override
    public int createUser(ReqJoinDto dto) {
        return this.authMapper.createUser(dto);
    }

    @Override
    public ResLoginDto selectUser(String username) {
        UserInfo userInfo = this.authMapper.selectUser(username);
        return this.authConverter.INSTANCE.convertLogin(userInfo);
    }

    @Override
    public int updateUserLogin(String username) {
        return this.authMapper.updateUserLogin(username);
    }

    @Override
    public int updateTrial(ReqLoginDto dto) {
        return this.authMapper.updateTrial(dto);
    }

    @Override
    public int updateUserStatus(ReqLoginDto dto) {
        return this.authMapper.updateUserStatus(dto);
    }

    @Override
    public int updateUserLogout(String username) {
        return this.authMapper.updateUserLogout(username);
    }

    @Override
    public ResFindIdDto findId(String phone) {
        FindIdInfo findIdInfo = this.authMapper.findId(phone);
        return this.authConverter.INSTANCE.convertFindId(findIdInfo);
    }

    @Override
    public ResFindPwDto findPw(ReqFindPwDto dto) {
        FindPwInfo findPwInfo = this.authMapper.findPw(dto);
        return this.authConverter.INSTANCE.convertFindPw(findPwInfo);
    }

    @Override
    public int modPw(ReqModPwDto dto) {
        return this.authMapper.modPw(dto);
    }
}
