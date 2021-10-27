package com.sun.tingle.member.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    MemberService memberService;
 
    @Override
    public void sendId(String email, String memberId) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setFrom("Tingle@gmail.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Tingle 아이디 찾기 안내");
            messageHelper.setText(  "<html><body><div style=\"display: flex; flex-direction: column;\"> \n" +
                    "<h1>Tingle</h1>\n" +
                    "<h2>아이디 찾기 결과입니다.</h2>\n" +
                    "<span>아이디 : "+memberId+"</span>\n" +
                    "</div></body></html>", true);

            javaMailSender.send(message);
        }catch(MessagingException e){
            log.error("가입 메일 발송 실패 :{}", e);
        }
    }

    @Override
    public String SendPasswordCode(String email, String name) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String code = getRamdomPassword();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(email);
            messageHelper.setSubject("Tingle 인증 코드 안내");
            messageHelper.setText(  "<html><body><div style=\"display: flex; flex-direction: column;\"> \n" +
                    " <h1>인-라인</h1>\n" +
                    "<h2>비밀번호 초기화를 원하시나요?</h2>\n" +
                    "<p>" + name + "님, 안녕하세요. 비밀번호 초기화 코드를 발급해 드렸습니다.<br>" +
                    "비밀번호 초기화를 원하시면 아래 코드를 통해 인증 후, 비밀번호를 변경하실 수 있습니다.</p>\n" +
                    "<p style=\"font-weight: bold;\"><span>인증 코드 : </span>"+code+"</p>\n" +
                    "</div></body></html>", true);

            javaMailSender.send(message);
        }catch(MessagingException e){
            log.error("가입 메일 발송 실패 :{}", e);
        }
        return code;
    }

    public String getRamdomPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                };
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;

        for (int i = 0; i < 8; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
