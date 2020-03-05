package com.progmasters.mars.service;

import com.progmasters.mars.domain.ConfirmationToken;
import com.progmasters.mars.domain.IndividualUser;
import com.progmasters.mars.repository.ConfirmationTokenRepository;
import com.progmasters.mars.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Value("${email.send.subject}")
    private String subject;
    @Value("${email.send.text}")
    private String text;
    @Value("${email.send.confirmation}")
    private String confirmationUrl;

    public UserService(UserRepository userRepository, EmailService emailService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    private void sendConfirmationEmail(IndividualUser individualUser) {
        //TODO get mail from parameter, add hash
        ConfirmationToken userToken= new ConfirmationToken(individualUser);
        confirmationTokenRepository.save(userToken);

        emailService.sendMsg(individualUser.getEmail(), subject, text+"\n"+confirmationUrl+userToken.getToken());
    }

    public void confirmUser(String token)
    {
        ConfirmationToken userToken = confirmationTokenRepository.findByToken(token);

        if (userToken!=null)
        {
            confirmationTokenRepository.deleteById(userToken.getId());
        }
    }

    private boolean isRegistrationConfirmed(IndividualUser individualUser)
    {
        return false;
    }
}
