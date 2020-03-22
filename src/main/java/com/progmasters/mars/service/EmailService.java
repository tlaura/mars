package com.progmasters.mars.service;

import com.progmasters.mars.domain.ConfirmationToken;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.MailData;
import com.progmasters.mars.repository.ConfirmationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EmailService {
    //TODO Az email küldés eléggé be tudja lassítani a flowt, ezért ezt sokszor érdemes külön szálra kiszervezni...
    // Ez elég melós, főleg ha akarunk esetleg feedbacket is, hogy kiment-e rendben a levél...
    // Viszont sokszor megéri, hiszen pl a regisztrációs oldal addig fog váratni minket, hogy sikeres volt-e, amíg a
    // teljes levélküldés folyamat be nem fejeződött...

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${email.send.subject}")
    private String subject;
    @Value("${email.send.text}")
    private String text;
    @Value("${email.send.confirmation}")
    private String confirmationUrl;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepository, AccountService accountService) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.accountService = accountService;
    }

    @Async
    public CompletableFuture<Long> sendMsg(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
        long finished = System.currentTimeMillis();
        return CompletableFuture.completedFuture(finished);
    }

    public void sendEmailToGivenEmail(MailData mailData) {
        //todo refactor
        long start = System.currentTimeMillis();
        CompletableFuture<Long> msgSent = sendMsg(mailData.getToEmail(), mailData.getName() + "(" + mailData.getFromEmail() + ")" + "-" + mailData.getSubject(), mailData.getText());
        try {
            logger.info("Elapsed time: " + (msgSent.get() - start));
        } catch (InterruptedException | ExecutionException e) {
            logger.warn(e.getMessage());
        }
    }

    public void sendConfirmationEmail(ProviderAccount user) {

        ConfirmationToken userToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(userToken);
        long start = System.currentTimeMillis();

        CompletableFuture<Long> msgSent = sendMsg(user.getEmail(), subject, text + "\n" + confirmationUrl + userToken.getToken());
        try {
            logger.info("Elapsed time on message sent:\t" + (msgSent.get() - start));
        } catch (InterruptedException | ExecutionException e) {
            logger.warn(e.getMessage());
        }
    }

    public void confirmUser(String token) {
        ConfirmationToken userToken = confirmationTokenRepository.findByToken(token);

        if (userToken != null) {
            userToken.setConfirmed(true);
            confirmationTokenRepository.save(userToken);
        } else {
            throw new EntityNotFoundException("not valid confirmation link");
        }
    }

    public void removeConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }

    public List<ConfirmationToken> findAllConfirmationToken() {
        return confirmationTokenRepository.findAll();
    }

    public boolean isUserConfirmed(String email) {
        ProviderAccount account = accountService.findByEmail(email);
        return confirmationTokenRepository.findByUser(account).orElseThrow(() -> new EntityNotFoundException("No account found by given email")).isConfirmed();
    }
}
