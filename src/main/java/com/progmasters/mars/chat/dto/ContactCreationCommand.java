package com.progmasters.mars.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactCreationCommand {

    private String fromEmail;

    private String toEmail;
}
