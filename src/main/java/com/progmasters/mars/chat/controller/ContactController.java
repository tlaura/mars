package com.progmasters.mars.chat.controller;

import com.progmasters.mars.chat.dto.ContactCreationCommand;
import com.progmasters.mars.chat.dto.ContactsData;
import com.progmasters.mars.chat.dto.MessageData;
import com.progmasters.mars.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ChatService chatService;

    public ChatService getChatService() {
        return chatService;
    }

    @Autowired
    public ContactController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Void> saveContact(@RequestBody ContactCreationCommand contactCreationCommand) {
        chatService.saveContact(contactCreationCommand);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContactsData>> getContactsByEmail(@RequestParam("email") String email) {
        List<ContactsData> contactsData = chatService.getContactsByEmail(email);

        return new ResponseEntity<>(contactsData, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<MessageData>> getChatHistory(@RequestParam("fromEmail") String fromEmail, @RequestParam("toEmail") String toEmail) {
        List<MessageData> chatHistory = chatService.getChatHistory(fromEmail, toEmail);

        return new ResponseEntity<>(chatHistory, HttpStatus.OK);
    }
}
