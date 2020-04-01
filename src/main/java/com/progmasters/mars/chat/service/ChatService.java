package com.progmasters.mars.chat.service;

import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.chat.domain.Contact;
import com.progmasters.mars.chat.dto.ContactCreationCommand;
import com.progmasters.mars.chat.dto.ContactsData;
import com.progmasters.mars.chat.dto.MessageData;
import com.progmasters.mars.chat.repository.ContactRepository;
import com.progmasters.mars.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatService {

    private final MessageRepository messageRepository;
    private final ContactRepository contactRepository;
    private final AccountService accountService;

    @Autowired
    public ChatService(MessageRepository messageRepository, ContactRepository contactRepository, AccountService accountService) {
        this.messageRepository = messageRepository;
        this.contactRepository = contactRepository;
        this.accountService = accountService;
    }

    public void saveMessage(MessageData messageData) {

    }

    public void saveContact(ContactCreationCommand contactCreationCommand) {
        User fromUser = accountService.findByEmail(contactCreationCommand.getFromEmail());
        User toUser = accountService.findByEmail(contactCreationCommand.getToEmail());
        Contact contact = new Contact(fromUser, toUser);
        contactRepository.save(contact);
    }

    public List<ContactsData> getContactsByEmail(String email) {
        List<ContactsData> contactsDataList = new ArrayList<>();

        accountService.getRecievingUsersByEmail(email).stream().map(ContactsData::new).forEach(contactsDataList::add);
        accountService.getProposingUsersByEmail(email).stream().map(ContactsData::new).forEach(contactsDataList::add);

        return contactsDataList;
    }

    public List<MessageData> getChatHistory(String fromEmail, String toEmail) {
        Contact connection = contactRepository.findConnectionByUsers(fromEmail, toEmail);

        return null;
    }


}
