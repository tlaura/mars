package com.progmasters.mars.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageData {

    private String fromName;

    private String fromEmail;

    private String toName;

    private String toEmail;

    private String proposingName;

    private String proposingEmail;

    private String recieverName;

    private String recieverEmail;

    //   @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    private String text;

    private String loginState;

    public MessageData(String fromName, String fromEmail, String toName, String toEmail, LocalDateTime date, String text) {
        this.fromName = fromName;
        this.fromEmail = fromEmail;
        this.toName = toName;
        this.toEmail = toEmail;
        this.date = date;
        this.text = text;
    }
}
