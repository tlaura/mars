package com.progmasters.mars.mail;

import lombok.Getter;

@Getter
public class MailData {
    private String fromEmail;
    private String subject;
    private String text;
    private String name;
    private String toEmail;
}
