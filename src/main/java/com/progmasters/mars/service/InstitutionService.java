package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.dto.*;
import com.progmasters.mars.repository.InstitutionRepository;
import com.progmasters.mars.util.ExcelFileLoader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final GeocodeService geocodeService;
    private final AccountService accountService;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, GeocodeService geocodeService, AccountService accountService) {
        this.institutionRepository = institutionRepository;
        this.geocodeService = geocodeService;
        this.accountService = accountService;
    }

    public List<InstitutionListData> getInstitutionList() {
        //  Pageable pageable= PageRequest.of(page,size);
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
    }

    public InstitutionDetailsData getInstitutionDetails(Long id) {
        return new InstitutionDetailsData(findById(id));
    }

    public Long saveToAccount(InstitutionCreationCommand institutionCreationCommand, Long accountId) {
        ProviderAccount providerAccount = accountService.findById(accountId);
        Institution institution;
        if (institutionCreationCommand.getId() != null) {
            institution = findById(institutionCreationCommand.getId());
        } else {
            institution = createInstitution(institutionCreationCommand);
        }
        institution.setProviderAccount(providerAccount);

        return institution.getId();
    }

    public Institution createInstitution(InstitutionCreationCommand institutionCreationCommand) {
        String address = institutionCreationCommand.getZipcode() + " " + institutionCreationCommand.getCity() + " " + institutionCreationCommand.getAddress();
        GeoLocation geoLocation = getGeoLocationByAddress(address);
        Institution institution = new Institution(institutionCreationCommand, geoLocation);
        institutionRepository.save(institution);

        return institution;
    }

    private GeoLocation getGeoLocationByAddress(String address) {
        return geocodeService.getGeoLocation(address);
    }


    public void updateInstitutionLocation(GeoLocationData geoLocationData, Long id) {
        Institution institution = findById(id);

        institution.setLongitude(geoLocationData.getLongitude());
        institution.setLatitude(geoLocationData.getLatitude());

        institutionRepository.save(institution);
    }

    public Institution findById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no such entity found"));
    }

    public List<InstitutionDetailsData> getInstitutionDetailsDataList() {
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionDetailsData::new)
                .collect(Collectors.toList());
    }

    public void importInstitutions(MultipartFile excelDataFile) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excelDataFile.getInputStream());
            List<ExcelFileLoader> rows = ExcelFileLoader.getRowList(workbook);
            for (ExcelFileLoader row : rows) {
                String address = row.getZipcode() + " " + row.getCity() + " " + row.getAddress();
                GeoLocation geoLocation = getGeoLocationByAddress(address);
                Institution institution = new Institution(row, geoLocation);
                institutionRepository.save(institution);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
