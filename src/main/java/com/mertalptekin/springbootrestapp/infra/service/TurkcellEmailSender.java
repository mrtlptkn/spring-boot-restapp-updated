package com.mertalptekin.springbootrestapp.infra.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("turkcell")
@Primary
public class TurkcellEmailSender implements IEmailSender {
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println(to + subject + body);
        System.out.println("Turkcell email sent to " + to);
    }
}
