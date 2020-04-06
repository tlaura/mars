package com.progmasters.mars.account_institution.institution.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.domain.InstitutionPetition;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDeleteListData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.repository.ConfirmationInstitutionRepository;
import com.progmasters.mars.account_institution.institution.repository.InstitutionPetitionRepository;
import com.progmasters.mars.account_institution.institution.repository.InstitutionRepository;
import com.progmasters.mars.map.MapService;
import com.progmasters.mars.map.dto.GeoLocationData;
import com.progmasters.mars.util.ExcelFileLoader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstitutionService {

    @Value("${regex.validator.email}")
    private String email;

    private final InstitutionRepository institutionRepository;
    private final InstitutionPetitionRepository institutionPetitionRepository;
    private final ConfirmationInstitutionRepository confirmationInstitutionRepository;
    private final MapService mapService;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, InstitutionPetitionRepository institutionPetitionRepository, ConfirmationInstitutionRepository confirmationInstitutionRepository, MapService mapService) {
        this.institutionRepository = institutionRepository;
        this.institutionPetitionRepository = institutionPetitionRepository;
        this.confirmationInstitutionRepository = confirmationInstitutionRepository;
        this.mapService = mapService;
    }

    public List<InstitutionListData> getAllInstitutions() {
        return institutionRepository.findAll().stream().map(InstitutionListData::new).collect(Collectors.toList());
    }


    public List<Institution> findInstitutionByProviderType(ProviderType providerType) {
        return institutionRepository.findInstitutionsByProviderType(providerType);
    }

    public List<Institution> getInstitutionsByAccount(ProviderAccount providerAccount) {
        return institutionRepository.getInstitutionsByAccount(providerAccount);
    }

    public List<Institution> findInstitutionsWithoutProvider() {
        return institutionRepository.findInstitutionsWithoutProvider();
    }

    public List<Institution> findInstitutionsWithProvider() {
        return institutionRepository.findInstitutionsWithProvider();
    }


    public InstitutionDetailsData getInstitutionDetailsById(Long id) {
        return new InstitutionDetailsData(findById(id));
    }

    public Institution saveInstitution(Institution institution) throws NotFoundException {
        String address = institution.getZipcode() + " " + institution.getCity() + " " + institution.getAddress();
        GeoLocationData geoLocationData = getGeoLocationByAddress(address);
        institution.setLatitude(geoLocationData.getLatitude());
        institution.setLongitude(geoLocationData.getLongitude());
        institutionRepository.save(institution);
        return institution;
    }

    private GeoLocationData getGeoLocationByAddress(String address) throws NotFoundException {
        return mapService.getGeoLocation(address);
    }

    public Institution findById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no such entity found"));
    }

    public void importInstitutions(MultipartFile excelDataFile) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excelDataFile.getInputStream());
            List<ExcelFileLoader> rows = ExcelFileLoader.getRowList(workbook);
            List<Institution> saveList = new ArrayList<>();
            for (ExcelFileLoader row : rows) {
                String address = row.getZipcode() + " " + row.getCity() + " " + row.getAddress();
                GeoLocationData geoLocationData = getGeoLocationByAddress(address);
                Institution institution = new Institution(row, geoLocationData);
                if (institutionRepository.findAllByName(institution.getName()).isEmpty()
                        && institutionRepository.findAllByEmail(institution.getEmail()).isEmpty()
                        && isValidInstitution(institution)) {
                    //   institutionRepository.save(institution);
                    saveList.add(institution);
                }
            }
            saveInstitutionConcurrently(saveList).get();
        } catch (IOException | NotFoundException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Async
    CompletableFuture<List<Institution>> saveInstitutionConcurrently(List<Institution> institutions) {
        return CompletableFuture.completedFuture(institutionRepository.saveAll(institutions));
    }


    private boolean isValidInstitution(Institution institution) {
        boolean isValid = institution.getZipcode() < 10000 && institution.getZipcode() > 999 &&
                institution.getDescription().length() > 30 && institution.getDescription().length() < 200 &&
                institution.getEmail().matches(email);
        return isValid;
    }

    List<Institution> findAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Institution findByName(String name) {
        return institutionRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("no such entity found with given name"));
    }

    public void signInstitutionToDelete(Long id, String cause) {
        Institution institution = findById(id);
        InstitutionPetition institutionPetition;
        if (institution.getInstitutionPetition() != null) {
            institutionPetition = institutionPetitionRepository.findByInstituion(institution);
            Integer requests = institutionPetition.getNumberOfRequest();
            institutionPetition.setNumberOfRequest(++requests);
            institutionPetition.getCauses().add(cause);
        } else {
            institutionPetition = new InstitutionPetition(cause);
        }
        institutionPetition.setInstitution(institution);
        institutionPetitionRepository.save(institutionPetition);
    }


    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }

    public void rejectInsitutionDeletion(Long id) {
        Institution institution = findById(id);
        InstitutionPetition petition = institutionPetitionRepository.findByInstituion(institution);
        petition.setDeleteSign(false);
        institutionPetitionRepository.save(petition);
    }

    public List<InstitutionDeleteListData> getInstitutionsToDelete() {
        return institutionRepository.findInstitutionsSignedToDelete().stream().map(InstitutionDeleteListData::new).collect(Collectors.toList());
    }

    public ConfirmationInstitution findConfirmationInstitutionByName(String name) {
        return confirmationInstitutionRepository.findByName(name);
    }

}
