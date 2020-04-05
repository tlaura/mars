package com.progmasters.mars.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageData {

    private String fromName;

    private String fromEmail;

    private String toName;

    private String toEmail;

    //    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private ZonedDateTime date;

    private String text;

    private String loginState;

    public MessageData(String text, String proposingName, String proposingEmail, String recieverName, String recieverEmail, ZonedDateTime localDateTime) {
        this.fromName = proposingName;
        this.fromEmail = proposingEmail;
        this.toName = recieverName;
        this.toEmail = recieverEmail;
        this.text = text;
        this.date = localDateTime;
    }
}
