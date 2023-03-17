package edu.bzu.fdick.service.impl;

import edu.bzu.fdick.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendSimpleMail(String to, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cc_flipped@126.com"+"(嘿，宝贝)");
        message.setTo(to);
        message.setSubject("注册验证码");
        message.setText(content);
        mailSender.send(message);
    }
}
