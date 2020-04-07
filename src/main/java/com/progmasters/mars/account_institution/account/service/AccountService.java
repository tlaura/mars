package com.progmasters.mars.account_institution.account.service;

import com.progmasters.mars.account_institution.account.AccountType;
import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationToken;
import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationTokenRepository;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.account_institution.account.dto.*;
import com.progmasters.mars.account_institution.account.passwordtoken.PasswordToken;
import com.progmasters.mars.account_institution.account.passwordtoken.PasswordTokenRepository;
import com.progmasters.mars.account_institution.account.repository.ProviderAccountRepository;
import com.progmasters.mars.account_institution.account.repository.UserRepository;
import com.progmasters.mars.account_institution.account.security.AuthenticatedUserDetails;
import com.progmasters.mars.account_institution.connector.dto.AccountInstitutionListData;
import com.progmasters.mars.account_institution.connector.repository.AccountInstitutionConnectorRepository;
import com.progmasters.mars.chat.domain.LoginState;
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
    private EmailService emailService;
    private AccountInstitutionConnectorRepository accountInstitutionConnectorRepository;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          PasswordTokenRepository passwordTokenRepository,
                          EmailService emailService,
                          AccountInstitutionConnectorRepository accountInstitutionConnectorRepository) {
        this.providerAccountRepository = providerAccountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
        this.accountInstitutionConnectorRepository = accountInstitutionConnectorRepository;
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

    public void removeByEmail(String email) {
        User account = findByEmail(email);
        if (account instanceof ProviderAccount) {
            ((ProviderAccount) account).setTypes(null);
        }
        userRepository.deleteByEmail(email);
        //  providerAccountRepository.deleteByEmail(email);
    }

    public List<AccountInstitutionListData> findProvidersByAgeRange(Integer age) {

        List<AccountInstitutionListData> listByAgeRange = providerAccountRepository.findProviderAccountsByAgeRange(age).stream().map(AccountInstitutionListData::new).collect(Collectors.toList());
        listByAgeRange.forEach(accountInstitutionListData -> accountInstitutionListData.setAccountType(AccountType.PROVIDER.toString()));
        return listByAgeRange;
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
        return userRepository.findOptionalUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
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
       return (confirmationTokenRepository.findByUser(user) == null);
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
            confirmationTokenRepository.delete(userToken);
            //    userToken.setConfirmed(true);
            //     confirmationTokenRepository.save(userToken);
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
        providerAccount.setWebsite(providerUserDetails.getWebsite());
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
        User user = userRepository.findOptionalUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
        emailService.sendPasswordEmail(user);
    }

    public void updatePassword(String token, String newPassword) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("Given token not found: " + token));
        User user = passwordToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        passwordTokenRepository.delete(passwordToken);
        if (user instanceof ProviderAccount) {
            providerAccountRepository.save((ProviderAccount) user);
        } else {
            userRepository.save(user);
        }
    }

    public boolean updatePasswordOfLoggedInUser(PasswordChangeDetails passwordChangeDetails) {
        boolean isChangeConfirmed = false;

        User user = userRepository.findOptionalUserByEmail(passwordChangeDetails.getEmail()).orElseThrow(() -> new EntityNotFoundException("UserModel not found by given email: " + passwordChangeDetails.getEmail()));

        String givenOldPassword = passwordChangeDetails.getOldPassword();
        String oldPassword = user.getPassword();

        boolean oldPasswordsMatch = BCrypt.checkpw(givenOldPassword, oldPassword);
        boolean newPasswordsMatch = passwordChangeDetails.getPassword().equals(passwordChangeDetails.getConfirmPassword());

        if (oldPasswordsMatch && newPasswordsMatch) {
            user.setPassword(BCrypt.hashpw(passwordChangeDetails.getPassword(), BCrypt.gensalt()));
            isChangeConfirmed = true;
        }
        return isChangeConfirmed;
    }

    public boolean deleteUser(String loggedInUser) {
        boolean isDeleted = false;
        Optional<ProviderAccount> optionalAccount = providerAccountRepository.findByEmail(loggedInUser);
        if (optionalAccount.isPresent()) {
            ProviderAccount providerAccount = optionalAccount.get();
            Long id = providerAccount.getId();
            providerAccount.setTypes(null);
            accountInstitutionConnectorRepository.removeAllByProviderAccountId(id);
            providerAccountRepository.delete(providerAccount);
            isDeleted = true;
        } else {
            Optional<User> optionalUser = userRepository.findOptionalUserByEmail(loggedInUser);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                userRepository.delete(user);
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    public List<User> getRecievingUsersByEmail(String email) {
        return userRepository.findRecievingUsersByEmail(email);
    }

    public List<User> getProposingUsersByEmail(String email) {
        return userRepository.findProposingUserByEmail(email);
    }


    public UserDetailsData getUserDetails(String email) {

        User user = findByEmail(email);

        return new UserDetailsData(user);
    }

    public UserDetailsData updateUser(UserDetailsData userDetailsData, String email) {
        User foundUser = findByEmail(email);
        updateUserDetails(foundUser, userDetailsData);
        userRepository.save(foundUser);
        return new UserDetailsData(foundUser);
    }

    private void updateUserDetails(User user, UserDetailsData userDetailsData) {
        user.setName(userDetailsData.getName());
        user.setPhone(userDetailsData.getPhone());
        user.setWebsite(userDetailsData.getWebsite());
        user.setZipcode(userDetailsData.getZipcode());
        user.setCity(userDetailsData.getCity());
        user.setAddress(userDetailsData.getAddress());
        user.setNewsletter(userDetailsData.getNewsletter());
    }

    public void setLoginState(String email, LoginState loginState) {
        User user = findByEmail(email);
        user.setState(loginState);
        userRepository.save(user);
    }
}
