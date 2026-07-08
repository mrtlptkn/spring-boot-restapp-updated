package com.mertalptekin.springbootrestapp.infra.email;

public interface IEmailSender {
    void sendEmail(String to, String subject, String body);
}
