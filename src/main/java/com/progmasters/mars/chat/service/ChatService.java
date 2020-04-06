package com.progmasters.mars.chat.service;

import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.service.AccountService;
import com.progmasters.mars.chat.domain.Contact;
import com.progmasters.mars.chat.domain.LoginState;
import com.progmasters.mars.chat.domain.Message;
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
import java.util.stream.Collectors;

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

    public AccountService getAccountService() {
        return accountService;
    }

    public void saveMessage(MessageData messageData) {
        Contact connectionByUsers = contactRepository.findConnectionByUsers(messageData.getFromEmail(), messageData.getToEmail());
        if (messageData.getLoginState() != null) {
            LoginState loginState = LoginState.valueOf(messageData.getLoginState());
            accountService.setLoginState(messageData.getFromEmail(), loginState);
        }
        Message message = new Message(messageData.getText(), messageData.getFromName(), messageData.getFromEmail(), messageData.getToName(), messageData.getToEmail());
        connectionByUsers.setFromAccountActive(true);
        connectionByUsers.setToAccountActive(true);
        message.setContact(connectionByUsers);
        messageRepository.save(message);
    }

    public void saveContact(ContactCreationCommand contactCreationCommand) {
        User fromUser = accountService.findByEmail(contactCreationCommand.getFromEmail());
        User toUser = accountService.findByEmail(contactCreationCommand.getToEmail());
        List<Contact> multipleContacts = contactRepository.findMultipleContacts(fromUser, toUser);
        boolean isSelfMessaging = contactCreationCommand.getFromEmail().equalsIgnoreCase(contactCreationCommand.getToEmail());
        if (multipleContacts.isEmpty() && !isSelfMessaging) {
            Contact contact = new Contact(fromUser, toUser, true);
            contactRepository.save(contact);
        } else if (multipleContacts.size() == 1 && !isSelfMessaging) {
            setCurrentContactActive(fromUser, multipleContacts);
        }
    }

    private void setCurrentContactActive(User fromUser, List<Contact> multipleContacts) {
        Contact currentContact = multipleContacts.get(0);
        if (currentContact.getFromAccount().equals(fromUser)) {
            currentContact.setFromAccountActive(true);
        } else {
            currentContact.setToAccountActive(true);
        }
    }

    public List<ContactsData> getContactsByEmail(String email) {
        List<ContactsData> contactsDataList = new ArrayList<>();

        List<String> activeContactEmails = getActiveContactEmails(email);

        accountService.getRecievingUsersByEmail(email).stream()
                .filter(user -> activeContactEmails.contains(user.getEmail()))
                .map(ContactsData::new)
                .forEach(contactsDataList::add);
        accountService.getProposingUsersByEmail(email).stream()
                .filter(user -> activeContactEmails.contains(user.getEmail()))
                .map(ContactsData::new)
                .forEach(contactsDataList::add);

        return contactsDataList;
    }

    private List<String> getActiveContactEmails(String email) {
        List<Contact> contacts = contactRepository.findContactsByEmail(email);
        List<String> activeContactEmails = contacts.stream()
                .filter(contact ->
                        ((contact.getFromAccount().getEmail().equals(email) && contact.isFromAccountActive())
                                || (contact.getToAccount().getEmail().equals(email) && contact.isToAccountActive()))
                )
                .map(contact -> contact.getFromAccount().getEmail())
                .collect(Collectors.toList());

        activeContactEmails.addAll(contacts.stream()
                .filter(contact ->
                        ((contact.getFromAccount().getEmail().equals(email) && contact.isFromAccountActive())
                                || (contact.getToAccount().getEmail().equals(email) && contact.isToAccountActive()))
                )
                .map(contact -> contact.getToAccount().getEmail())
                .collect(Collectors.toList()));
        return activeContactEmails;
    }

    public List<MessageData> getChatHistory(String fromEmail, String toEmail) {
        List<MessageData> chatHistory = new ArrayList<>();
        Contact connection = contactRepository.findConnectionByUsers(fromEmail, toEmail);
        if (connection.getMessages() != null) {
            for (Message message : connection.getMessages()) {
                MessageData messageData = new MessageData(message.getText(), message.getProposingName(), message.getProposingEmail(), message.getRecieverName(), message.getRecieverEmail(), message.getDate());
                chatHistory.add(messageData);
            }
        }
        return chatHistory;
    }

    public void deleteContact(String fromEmail, String toEmail) {
        Contact contact = contactRepository.findConnectionByUsers(fromEmail, toEmail);
        if (fromEmail.equals(contact.getFromAccount().getEmail())) {
            contact.setFromAccountActive(false);
        } else {
            contact.setToAccountActive(false);
        }
        contactRepository.save(contact);
    }
}
