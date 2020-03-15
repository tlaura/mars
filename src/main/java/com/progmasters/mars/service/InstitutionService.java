package com.progmasters.mars.service;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.dto.*;
import com.progmasters.mars.repository.InstitutionRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
        //  Pageable pageable= PageRequest.of(page,size);
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
    }

    public InstitutionDetailsData getInstitutionDetails(Long id) {
        return new InstitutionDetailsData(findById(id));
    }


    public void createInstitution(InstitutionCreationForm institutionCreationForm) {
        String address = institutionCreationForm.getZipCode() + " " + institutionCreationForm.getCity() + " " + institutionCreationForm.getAddress();
        GeoLocation geoLocation = geocodeService.getGeoLocation(address);
        Institution institution = new Institution(institutionCreationForm, geoLocation);
        //todo get user id
        institutionRepository.save(institution);
    }

    public List<InstitutionListData> getInstitutionsByType(InstitutionType institutionType) {
        return institutionRepository.findAllByInstitutionType(institutionType).stream().map(InstitutionListData::new).collect(Collectors.toList());
    }

    //todo remove in the future
    public List<InstitutionListData> tempByType(InstitutionType institutionType) {
        List<InstitutionListData> listByType = institutionRepository.findAllByInstitutionType(institutionType).stream().map(InstitutionListData::new).collect(Collectors.toList());
        setLocation(listByType);
        return listByType;
    }

    public List<InstitutionListData> tempInstitutionList() {
        List<InstitutionListData> collect = institutionRepository.findAll()
                .stream()
                .map(InstitutionListData::new)
                .collect(Collectors.toList());
        setLocation(collect);

        return collect;
    }

    private void setLocation(List<InstitutionListData> collect) {
        for (InstitutionListData institution : collect) {
            String address = institution.getZipCode() + " " + institution.getCity() + " " + institution.getAddress();
            GeoLocation geoLocation = geocodeService.getGeoLocation(address);
            institution.setLatitude(geoLocation.getLatitude());
            institution.setLongitude(geoLocation.getLongitude());
        }
    }


    public void updateInstitutionLocation(GeoLocationData geoLocationData, Long id) {
        Institution institution = findById(id);

        institution.setLongitude(geoLocationData.getLongitude());
        institution.setLatitude(geoLocationData.getLatitude());

        institutionRepository.save(institution);
    }

    private Institution findById(Long id) {
        return institutionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no such entity found"));
    }

    public List<InstitutionDetailsData> getInstitutionDetailsDataList() {
        return institutionRepository.findAll()
                .stream()
                .map(InstitutionDetailsData::new)
                .collect(Collectors.toList());
    }

    public void importInstitutions(MultipartFile excelDataFile) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(excelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        // TODO: fit mandatory fields

        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) { //first row is a "header"
            InstitutionCreationForm institution = new InstitutionCreationForm();

            XSSFRow row = worksheet.getRow(i);
            if (isValidRow(row)) {
                institution.setName(row.getCell(0).getStringCellValue());
                institution.setZipCode(parseZipCodeFromAddress(row.getCell(1).getStringCellValue()));
                institution.setCity(parseCityFromAddress(row.getCell(1).getStringCellValue()));
                institution.setAddress(parseAddressFromAddress(row.getCell(1).getStringCellValue()));
                institution.setEmail(parseEmail(row.getCell(3).getStringCellValue()));
                institution.setDescription(row.getCell(5).getStringCellValue());

                createInstitution(institution);
            }
        }
    }

    private boolean isValidRow(XSSFRow row) {
        for (int i = 0; i < 6; i++) {
            if (row.getCell(i) == null) {
                return false;
            }
        }
        return true;
    }

    private String parseEmail(String stringCellValue) { // TODO: email list
        String[] stringCellValueArray = stringCellValue.split(", ");
        return stringCellValueArray[0];
    }

    //TODO: refactor !!

    private String parseAddressFromAddress(String stringCellValue) {
        String[] stringCellValueArray = stringCellValue.split(", ");
        return stringCellValueArray[1];
    }

    private String parseCityFromAddress(String stringCellValue) {
        String[] stringCellValueArray = stringCellValue.split(", ");
        return stringCellValueArray[0];
    }

    private Integer parseZipCodeFromAddress(String stringCellValue) {
        String[] stringCellValueArray = stringCellValue.split(", ");
        return Integer.parseInt(stringCellValueArray[2].split(" ")[0]);
    }
}
