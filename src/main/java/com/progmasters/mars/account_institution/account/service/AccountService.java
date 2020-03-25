package com.progmasters.mars.account_institution.account.service;

import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationToken;
import com.progmasters.mars.account_institution.account.confirmationtoken.ConfirmationTokenRepository;
import com.progmasters.mars.account_institution.account.domain.InstitutionType;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetails;
import com.progmasters.mars.account_institution.account.dto.ProviderUserDetailsEdit;
import com.progmasters.mars.account_institution.account.repository.ProviderAccountRepository;
import com.progmasters.mars.account_institution.institution.repository.InstitutionRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private ProviderAccountRepository providerAccountRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private InstitutionRepository insitutionRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public AccountService(ProviderAccountRepository providerAccountRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          InstitutionRepository insitutionRepository) {
        this.insitutionRepository = insitutionRepository;
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

    public List<ProviderAccount> getAccountsByType(InstitutionType institutionType) {
        return providerAccountRepository.findByType(institutionType);
    }

    public ProviderAccount findById(Long id) {
        return providerAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No account found by given id:\t" + id));
    }

    ProviderAccount findByEmail(String email) {
        return providerAccountRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No account found by given email:\t" + email));
    }

    public ProviderUserDetailsEdit getProviderAccountEditDetailsByEmail(String loggedInUserEmail) {
        return new ProviderUserDetailsEdit(findByEmail(loggedInUserEmail));
    }

    public ProviderUserDetailsEdit updateProviderAccount(ProviderUserDetailsEdit providerUserDetailsEdit, String loggedInUserEmail) {
        ProviderAccount providerAccount = findByEmail(loggedInUserEmail);
        updateAccountFields(providerUserDetailsEdit, providerAccount);

        return new ProviderUserDetailsEdit(providerAccount);
    }

    List<ProviderAccount> findAllAccounts() {
        return providerAccountRepository.findAll();
    }

    void updateAccountFields(ProviderUserDetailsEdit providerUserDetailsEdit, ProviderAccount providerAccount) {
        providerAccount.setName(providerUserDetailsEdit.getName());
        providerAccount.setProviderServiceName(providerUserDetailsEdit.getProviderServiceName());
//        providerAccount.setPassword(providerUserDetailsEdit.getPassword());
        providerAccount.setPhone(providerUserDetailsEdit.getPhone());
        providerAccount.setZipcode(providerUserDetailsEdit.getZipcode());
        providerAccount.setCity(providerUserDetailsEdit.getCity());
        providerAccount.setAddress(providerUserDetailsEdit.getAddress());
        providerAccount.setNewsletter(providerUserDetailsEdit.getNewsletter());
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

    public ProviderAccount updateProviderAccountDetails(ProviderUserDetails providerUserDetails, Long id) {
        Optional<ProviderAccount> optionalAcc = providerAccountRepository.findById(id);
        if (optionalAcc.isPresent()) {
            ProviderAccount providerAccount = optionalAcc.get();
            updateDetails(providerUserDetails, providerAccount);
            return providerAccount;
        } else {
            return null;
        }
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
