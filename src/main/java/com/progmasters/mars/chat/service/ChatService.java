package com.progmasters.mars.chat.service;

import com.progmasters.mars.chat.dto.MessageData;
import com.progmasters.mars.chat.repository.ContactRepository;
import com.progmasters.mars.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatService {

    private final MessageRepository messageRepository;
    private final ContactRepository contactRepository;

    @Autowired
    public ChatService(MessageRepository messageRepository, ContactRepository contactRepository) {
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
    }

    public void saveMessage(MessageData messageData) {

    }


}
