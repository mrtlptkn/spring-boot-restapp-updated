package com.mertalptekin.springbootrestapp.infra.service;

public interface IEmailSender {
    void sendEmail(String to, String subject, String body);
}
