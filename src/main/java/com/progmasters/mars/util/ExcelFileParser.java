package com.progmasters.mars.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;


public class ExcelFileParser {

    private static int ADDRESS_POS = 0;
    private static int CITY_POS = 1;
    private static int ZIPCODE_POS = 2;
    //TODO: customize... or check

    private static int name_col;
    private static int address_col;
    private static int email_col;
    private static int phone_col;
    private static int website_col;
    private static int description_col;
    private static int institutionType_col;
    private static int all_col;
    // TODO: fix mandatory fields

    private String name;
    private Integer zipCode;
    private String city;
    private String address;
    private String email;
    private String phone;
    private String website;
    private String description;
    private String institutionType;

    private ExcelFileParser() {
    }

    private ExcelFileParser(XSSFRow row) {
        name = row.getCell(name_col).getStringCellValue();
        zipCode = parseZipCodeFromAddress(row.getCell(address_col).getStringCellValue());
        city = parseCityFromAddress(row.getCell(address_col).getStringCellValue());
        address = parseAddressFromAddress(row.getCell(address_col).getStringCellValue());
        email = parseEmail(row.getCell(email_col).getStringCellValue());
        description = row.getCell(description_col).getStringCellValue();
        institutionType = row.getCell(institutionType_col).getStringCellValue();
        phone = row.getCell(phone_col).getStringCellValue(); // TODO: VALID? more phone?
        website = row.getCell(website_col).getStringCellValue(); // TODO: VALID? more phone?
    }

    public static List<ExcelFileParser> getParsedList(XSSFWorkbook workbook) {
        List<ExcelFileParser> parsedList = new ArrayList<>();
        XSSFSheet worksheet = workbook.getSheetAt(0); //TODO: more sheets?
        setCols(worksheet);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { //first row is maybe a "header"
            XSSFRow row = worksheet.getRow(i);
            if (isValidRow(row)) {
                parsedList.add(new ExcelFileParser(row));
            }
        }
        return parsedList;
    }

    private static void setCols(XSSFSheet worksheet) {
        XSSFRow row = worksheet.getRow(0);
        int i = 0;
        while (row.getCell(i) != null) {
            switch (row.getCell(i).getStringCellValue()) {
                case "name":
                    name_col = i;
                    break;
                case "address":
                    address_col = i;
                    break;
                case "phone":
                    phone_col = i;
                    break;
                case "email":
                    email_col = i;
                    break;
                case "website":
                    website_col = i;
                    break;
                case "description":
                    description_col = i;
                    break;
                case "type":
                    institutionType_col = i;
                    break;
                default:
            }
            i++;
        }
        all_col = i;
    }

    private static boolean isValidRow(XSSFRow row) {
        for (int i = 0; i < all_col; i++) {
            if (row.getCell(i) == null) {
                return false;
            }
        }
        return true;
    }

    private String parseAddressFromAddress(String stringCellValue) {
        String[] stringCellValueArray = stringCellValue.split(", "); //TODO: check splitter
        return stringCellValueArray[ADDRESS_POS];
    }

    private String parseCityFromAddress(String stringCellValue) {
        String[] stringCellValueArray = stringCellValue.split(", ");
        return stringCellValueArray[CITY_POS];
    }


    private Integer parseZipCodeFromAddress(String stringCellValue) {
        String[] stringCellValueArray = stringCellValue.split(", ");
        return Integer.parseInt(stringCellValueArray[ZIPCODE_POS].split(" ")[0]);
    }

    private String parseEmail(String stringCellValue) { // TODO: email list
        String[] stringCellValueArray = stringCellValue.split(", ");
        return stringCellValueArray[0];
    }


    public String getName() {
        return name;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
