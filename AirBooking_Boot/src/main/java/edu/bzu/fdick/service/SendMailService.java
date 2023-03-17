package edu.bzu.fdick.service;

import org.springframework.stereotype.Service;


public interface SendMailService {
    void sendSimpleMail(String to, String content);
}
