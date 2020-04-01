package com.progmasters.mars.chat.controller;

import com.progmasters.mars.chat.dto.MessageData;
import com.progmasters.mars.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic")
    public MessageData sendMessage(@Payload MessageData messageData) {

        chatService.saveMessage(messageData);

        return messageData;
    }

}
