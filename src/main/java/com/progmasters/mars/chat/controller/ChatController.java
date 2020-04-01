package com.progmasters.mars.chat.controller;

import com.progmasters.mars.chat.dto.MessageData;
import com.progmasters.mars.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }


    @MessageMapping("/send/message")
    public MessageData sendMessage(@Payload MessageData messageData) {
        chatService.saveMessage(messageData);
        if (messageData.getToEmail() != null && !messageData.getToEmail().equals("")) {
            this.simpMessagingTemplate.convertAndSend(messageData.getToEmail(), messageData);
            this.simpMessagingTemplate.convertAndSend(messageData.getFromEmail(), messageData);
        }
        return messageData;
    }


}
