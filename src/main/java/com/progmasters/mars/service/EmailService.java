package com.progmasters.mars.service;

import com.progmasters.mars.domain.ConfirmationToken;
import com.progmasters.mars.domain.IndividualUser;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenRepository confirmationTokenRepository;


    @Value("${email.send.subject}")
    private String subject;
    @Value("${email.send.text}")
    private String text;
    @Value("${email.send.confirmation}")
    private String confirmationUrl;

    public EmailService(JavaMailSender javaMailSender, ConfirmationTokenRepository confirmationTokenRepository) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }


    private void sendMsg(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }


    public void sendConfirmationEmail(ProviderAccount user) {
        //   User user = userRepository.findById(1l).get();

        ConfirmationToken userToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(userToken);

        sendMsg(user.getEmail(), subject, text + "\n" + confirmationUrl + userToken.getToken());
    }

    public void confirmUser(String token) {
        ConfirmationToken userToken = confirmationTokenRepository.findByToken(token);

        if (userToken != null) {
            userToken.setConfirmed(true);
            confirmationTokenRepository.save(userToken);
            //  confirmationTokenRepository.deleteById(userToken.getId());
        } else {
            throw new EntityNotFoundException("not valid confirmation link");
        }
    }

    private boolean isRegistrationConfirmed(IndividualUser individualUser) {
        return false;
    }
}
