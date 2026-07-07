package com.mertalptekin.springbootrestapp.infra.service;

import org.springframework.stereotype.Component;

@Component("sendGrid")
public class SendGridEmailSender implements IEmailSender {
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("Sending email with SendGrid to: " + to);
    }
}
