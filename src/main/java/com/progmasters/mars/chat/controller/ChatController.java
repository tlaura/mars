package com.progmasters.mars.chat.controller;

import com.progmasters.mars.chat.dto.MessageData;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic")
    public MessageData sendMessage(@Payload MessageData messageData) {


        return messageData;
    }

}
