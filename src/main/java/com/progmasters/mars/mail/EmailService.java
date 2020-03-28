package com.progmasters.mars.mail;

import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationToken;
import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationTokenRepository;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.passwordtoken.PasswordToken;
import com.progmasters.mars.account_institution.account.passwordtoken.PasswordTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordTokenRepository passwordTokenRepository;

    @Value("${email.send.subject}")
    private String subject;
    @Value("${email.send.subject}")
    private String subjectPassword;
    @Value("${email.send.text}")
    private String text;
    @Value("${email.send.textPassword}")
    private String textPassword;
    @Value("${email.send.confirmation}")
    private String confirmationUrl;
    @Value("${email.send.password}")
    private String passwordUrl;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepository, PasswordTokenRepository passwordTokenRepository) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.passwordTokenRepository = passwordTokenRepository;
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
            log.info("Elapsed time: " + (msgSent.get() - start));
        } catch (InterruptedException | ExecutionException e) {
            log.warn(e.getMessage());
        }
    }

    public void sendConfirmationEmail(ProviderAccount user) {

        ConfirmationToken userToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(userToken);
        long start = System.currentTimeMillis();

        CompletableFuture<Long> msgSent = sendMsg(user.getEmail(), subject, text + "\n" + confirmationUrl + userToken.getToken());
        try {
            log.info("Elapsed time on message sent:\t" + (msgSent.get() - start));
        } catch (InterruptedException | ExecutionException e) {
            log.warn(e.getMessage());
        }
    }

    public void sendPasswordEmail(ProviderAccount providerAccount) {
        PasswordToken providerAccountPasswordToken = new PasswordToken(providerAccount);
        passwordTokenRepository.save(providerAccountPasswordToken);
        long start = System.currentTimeMillis();

        CompletableFuture<Long> msgSent = sendMsg(providerAccount.getEmail(), subjectPassword, textPassword + "\n" + passwordUrl + providerAccountPasswordToken.getToken());
        try {
            log.info("Elapsed time on message sent:\t" + (msgSent.get() - start));
        } catch (InterruptedException | ExecutionException e) {
            log.warn(e.getMessage());
        }
    }
}
