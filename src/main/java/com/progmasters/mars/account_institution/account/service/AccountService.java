package com.progmasters.mars.account_institution.account.service;

import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationToken;
import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationTokenRepository;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.dto.PasswordChangeDetails;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.account_institution.account.passwordtoken.PasswordToken;
import com.progmasters.mars.account_institution.account.passwordtoken.PasswordTokenRepository;
import com.progmasters.mars.account_institution.account.repository.ProviderAccountRepository;
import com.progmasters.mars.account_institution.account.repository.UserRepository;
import com.progmasters.mars.account_institution.account.security.AuthenticatedUserDetails;
import com.progmasters.mars.account_institution.connector.AccountInstitutionListData;
import com.progmasters.mars.mail.EmailService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final EmailService emailService;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          PasswordTokenRepository passwordTokenRepository, EmailService emailService) {
        this.providerAccountRepository = providerAccountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
    }

    public User save(UserCreationCommand userCreationCommand) {
        userCreationCommand.setPassword(passwordEncoder.encode(userCreationCommand.getPassword()));
        User user;
        if (userCreationCommand instanceof ProviderAccountCreationCommand) {
            ProviderAccount providerAccount = new ProviderAccount((ProviderAccountCreationCommand) userCreationCommand);
            user = providerAccount;
            providerAccountRepository.save(providerAccount);
        } else {
            user = new User(userCreationCommand);
            userRepository.save(user);
        }
        emailService.sendConfirmationEmail(user);
        return user;
    }

    public void removeById(Long id) {
        ProviderAccount account = findById(id);
        account.setTypes(null);

        providerAccountRepository.deleteById(id);
    }

    public List<AccountInstitutionListData> findProvidersByAgeRange(Integer age) {
        return providerAccountRepository.findProviderAccountsByAgeRange(age).stream().map(AccountInstitutionListData::new).collect(Collectors.toList());
    }

    public ProviderUserDetails getProviderAccountByEmail(String loggedInUserEmail) {
        ProviderUserDetails providerUserDetails = null;
        User loggedInUser = findByEmail(loggedInUserEmail);
        if (loggedInUser instanceof ProviderAccount) {
            providerUserDetails = new ProviderUserDetails((ProviderAccount) loggedInUser);
        }
        return providerUserDetails;
    }

    public List<ProviderAccount> getAccountsByType(ProviderType providerType) {
        return providerAccountRepository.findByType(providerType);
    }

    public ProviderUserDetails getUserDetailsById(Long id) {
        return new ProviderUserDetails(findById(id));
    }

    public ProviderAccount findById(Long id) {
        return providerAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No account found by given id:\t" + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
    }

    List<ProviderAccount> findAllAccounts() {
        return providerAccountRepository.findAll();
    }

    public List<ProviderAccount> findAllAccountsWithoutInstitution() {
        return providerAccountRepository.findProviderAccountWithoutInstitution();
    }

    public List<ProviderAccount> findAllAccountsWithInstitution() {
        return providerAccountRepository.findProviderAccountsWithInstitutions();
    }

    public boolean isUserConfirmed(String email) {
        User user = findByEmail(email);
        return confirmationTokenRepository.findByUser(user).orElseThrow(() -> new EntityNotFoundException("No account found by given email")).isConfirmed();
    }

    public List<ConfirmationToken> findAllConfirmationToken() {
        return confirmationTokenRepository.findAll();
    }

    public void removeConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }

    public AuthenticatedUserDetails getAuthenticatedUserDetails(String email) {
        return new AuthenticatedUserDetails(findByEmail(email));
    }

    public void confirmUserToken(String token) {
        ConfirmationToken userToken = confirmationTokenRepository.findByToken(token);

        if (userToken != null) {
            userToken.setConfirmed(true);
            confirmationTokenRepository.save(userToken);
        } else {
            throw new EntityNotFoundException("not valid confirmation link");
        }
    }

    public void updateProviderAccountDetails(ProviderUserDetails providerUserDetails, Long id) {
        ProviderAccount providerAccount = findById(id);
        updateDetails(providerUserDetails, providerAccount);
    }

    private void updateDetails(ProviderUserDetails providerUserDetails, ProviderAccount providerAccount) {
        providerAccount.setName(providerUserDetails.getName());
        providerAccount.setProviderServiceName(providerUserDetails.getProviderServiceName());
        providerAccount.setPhone(providerUserDetails.getPhone());
        providerAccount.setEmail(providerUserDetails.getEmail());
        providerAccount.setZipcode(providerUserDetails.getZipcode());
        providerAccount.setCity(providerUserDetails.getCity());
        providerAccount.setAddress(providerUserDetails.getAddress());
        providerAccount.setNewsletter(providerUserDetails.getNewsletter());
        providerAccount.setAgeGroupMin(providerUserDetails.getAgeGroupMin());
        providerAccount.setAgeGroupMax(providerUserDetails.getAgeGroupMax());
        if (!providerUserDetails.getTypes().isEmpty()) {
            providerAccount.setTypes(providerUserDetails.getTypes().stream().map(ProviderType::getTypeByHungarianName).collect(Collectors.toList()));
        }
    }

    public void setNewPassword(String email) {
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
        emailService.sendPasswordEmail(providerAccount);
    }

    public void updatePassword(String token, String newPassword) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);
        User user = passwordToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
            if (user instanceof ProviderAccount) {
                providerAccountRepository.save((ProviderAccount) user);
            } else {
                userRepository.save(user);
            }
        } else {
            throw new EntityNotFoundException("not valid password reset link");
        }
    }

    public boolean updatePasswordOfLoggedInUser(PasswordChangeDetails passwordChangeDetails) {
        boolean isChangeConfirmed = false;

        Optional<ProviderAccount> providerAccount = providerAccountRepository.findByEmail(passwordChangeDetails.getEmail());
        if (providerAccount.isPresent()) {
            String givenOldPassword = passwordChangeDetails.getOldPassword();
            String oldPassword = providerAccount.get().getPassword();

            boolean oldPasswordsMatch = BCrypt.checkpw(givenOldPassword, oldPassword);
            boolean newPasswordsMatch = passwordChangeDetails.getPassword().equals(passwordChangeDetails.getConfirmPassword());

            if (oldPasswordsMatch && newPasswordsMatch) {
                providerAccount.get().setPassword(BCrypt.hashpw(passwordChangeDetails.getPassword(), BCrypt.gensalt()));
                isChangeConfirmed = true;
            }
        }
        return isChangeConfirmed;
    }

    public boolean deleteUser(String loggedInUser) {
        boolean isDeleted = false;
        Optional<ProviderAccount> optionalAccount = providerAccountRepository.findByEmail(loggedInUser);
        if (optionalAccount.isPresent()) {
            ProviderAccount providerAccount = optionalAccount.get();
            providerAccount.setTypes(null);
            providerAccount.setAccountInstitutionConnectors(null);
            providerAccountRepository.delete(providerAccount);
            isDeleted = true;
        } else {
            Optional<User> optionalUser = userRepository.findByEmail(loggedInUser);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                userRepository.delete(user);
                isDeleted = true;
            }
        }
        return isDeleted;
    }
}
