package com.progmasters.mars.account_institution.institution.service;

import com.google.maps.errors.NotFoundException;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.dto.InstitutionCreationCommand;
import com.progmasters.mars.account_institution.institution.dto.InstitutionDetailsData;
import com.progmasters.mars.account_institution.institution.dto.InstitutionListData;
import com.progmasters.mars.account_institution.institution.location.GeoLocation;
import com.progmasters.mars.account_institution.institution.location.GeocodeService;
import com.progmasters.mars.account_institution.institution.repository.InstitutionRepository;
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

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository, GeocodeService geocodeService) {
        this.institutionRepository = institutionRepository;
        this.geocodeService = geocodeService;
    }

    public List<InstitutionListData> getInstitutionList() {
        //TODO Látom a szándék megvan, ezt a Pageable-t ajánlott mihamarabb bevezetni,
        // könnyű implementálni, és menő :)
        // Van rá valahol példám ha érdekel :)

        //      Pageable pageable= PageRequest.of(page,size);
        //       return institutionRepository.findAll(pageable).stream().map(InstitutionListData::new).collect(Collectors.toList());
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
    }

    public List<Institution> getInstitutionsByAccount(ProviderAccount providerAccount) {
        return institutionRepository.getInstitutionsByAccount(providerAccount);
    }


    public InstitutionDetailsData getInstitutionDetails(Long id) {
        return new InstitutionDetailsData(findById(id));
    }

    public Institution saveInstitution(InstitutionCreationCommand institutionCreationCommand) throws NotFoundException {
        String address = institutionCreationCommand.getZipcode() + " " + institutionCreationCommand.getCity() + " " + institutionCreationCommand.getAddress();
        GeoLocation geoLocation = getGeoLocationByAddress(address);
        Institution institution = new Institution(institutionCreationCommand, geoLocation);
        institutionRepository.save(institution);
        return institution;
    }

    private GeoLocation getGeoLocationByAddress(String address) throws NotFoundException {
        return geocodeService.getGeoLocation(address);
    }

    public Institution findById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no such entity found"));
    }

    public void importInstitutions(MultipartFile excelDataFile) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(excelDataFile.getInputStream());
            List<ExcelFileLoader> rows = ExcelFileLoader.getRowList(workbook);
            for (ExcelFileLoader row : rows) {
                String address = row.getZipcode() + " " + row.getCity() + " " + row.getAddress();
                GeoLocation geoLocation = getGeoLocationByAddress(address);
                Institution institution = new Institution(row, geoLocation);
                if (institutionRepository.findAllByName(institution.getName()).isEmpty()
                        && institutionRepository.findAllByEmail(institution.getEmail()).isEmpty()) {
                    institutionRepository.save(institution);
                }
            }
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
        }
    }

    public Institution findByName(String name) {
        return institutionRepository.findByName(name);
    }
}
