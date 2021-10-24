package com.fashion.celebrity.auth.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendCertMailUtil {
    private static Logger logger = LoggerFactory.getLogger(SendCertMailUtil.class);

    public static boolean sendMail(JavaMailSender javaMailSender, String email, String certCode) {
        boolean sendMail;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[Celebrity] 회원가입 인증 메일");
            message.setText("인증 번호는 [" + certCode + "] 입니다.");

            javaMailSender.send(message);

            sendMail = true;
        } catch (Exception ex) {
            logger.error("sendMail failed", ex);
            sendMail = false;
        }

        return sendMail;
    }
}
