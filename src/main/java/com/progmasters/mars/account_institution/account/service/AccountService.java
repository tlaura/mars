package com.progmasters.mars.account_institution.account.service;

import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationToken;
import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationTokenRepository;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.account_institution.account.repository.ProviderAccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          ConfirmationTokenRepository confirmationTokenRepository
    ) {
        this.providerAccountRepository = providerAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public ProviderAccount save(ProviderAccountCreationCommand providerAccountCreationCommand) {
        ProviderAccount providerAccount = new ProviderAccount(providerAccountCreationCommand);
        providerAccount.setPassword(passwordEncoder.encode(providerAccountCreationCommand.getPassword()));
        providerAccountRepository.save(providerAccount);

        return providerAccount;
    }

    public void removeById(Long id) {
        ProviderAccount account = findById(id);
        account.setTypes(null);

        providerAccountRepository.deleteById(id);
    }

    public ProviderUserDetails getProviderAccountByEmail(String loggedInUserEmail) {
        return new ProviderUserDetails(findByEmail(loggedInUserEmail));
    }

    public List<ProviderAccount> getAccountsByType(ProviderType providerType) {
        return providerAccountRepository.findByType(providerType);
    }

    public ProviderAccount findById(Long id) {
        return providerAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No account found by given id:\t" + id));
    }

    public ProviderAccount findByEmail(String email) {
        return providerAccountRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
    }

    public ProviderUserDetailsEdit getProviderAccountEditDetailsByEmail(String loggedInUserEmail) {
        return new ProviderUserDetailsEdit(findByEmail(loggedInUserEmail));
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
        ProviderAccount account = findByEmail(email);
        return confirmationTokenRepository.findByUser(account).orElseThrow(() -> new EntityNotFoundException("No account found by given email")).isConfirmed();
    }

    public List<ConfirmationToken> findAllConfirmationToken() {
        return confirmationTokenRepository.findAll();
    }

    public void removeConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
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
        providerAccount.setPassword(providerUserDetails.getPassword());
        providerAccount.setPhone(providerUserDetails.getPhone());
        providerAccount.setEmail(providerUserDetails.getEmail());
        providerAccount.setZipcode(providerUserDetails.getZipcode());
        providerAccount.setCity(providerUserDetails.getCity());
        providerAccount.setAddress(providerUserDetails.getAddress());
        providerAccount.setNewsletter(providerUserDetails.getNewsletter());
        providerAccount.setAgeGroupMin(providerUserDetails.getAgeGroupMin());
        providerAccount.setAgeGroupMax(providerUserDetails.getAgeGroupMax());
//        providerAccount.setTypes(providerUserDetails.getTypes().stream().map(InstitutionType::valueOf).collect(Collectors.toList()));
    }

//    public boolean deleteInstitutionOfAccountById(String loggedInUser, Long institutionId) {
//        Optional<Institution> optionalInstitution = insitutionRepository.findById(institutionId);
//
//        if (optionalInstitution.isPresent()) {
//            Institution institution = optionalInstitution.get();
//            institution.setProviderAccount(null);
//            return true;
//        } else {
//            return false;
//        }
//    }
}
