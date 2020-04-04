package com.progmasters.mars.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactCreationCommand {

    private String fromEmail;

    private String toEmail;
}
