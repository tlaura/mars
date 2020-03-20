package com.progmasters.mars.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;


public class ExcelFileLoader {

    private static int name_col;
    private static int zipcode_col;
    private static int city_col;
    private static int address_col;
    private static int email_col;
    private static int phone_col;
    private static int website_col;
    private static int description_col;

    private static List<Integer> mandatory_cols = new ArrayList<>();

    private String name;
    private Integer zipcode;
    private String city;
    private String address;
    private String email;
    private String phone;
    private String website;
    private String description;


    private ExcelFileLoader() {
    }

    private ExcelFileLoader(XSSFRow row) {
        name = row.getCell(name_col).getStringCellValue();
        zipcode = Integer.parseInt(row.getCell(zipcode_col).getStringCellValue());
        city = row.getCell(city_col).getStringCellValue();
        address = row.getCell(address_col).getStringCellValue();
        email = row.getCell(email_col).getStringCellValue();
        description = row.getCell(description_col).getStringCellValue();
        phone = row.getCell(phone_col).getStringCellValue();
        if (row.getCell(website_col) != null) {
            website = row.getCell(website_col).getStringCellValue();
        }
    }

    public static List<ExcelFileLoader> getRowList(XSSFWorkbook workbook) {
        List<ExcelFileLoader> parsedList = new ArrayList<>();
        XSSFSheet worksheet = workbook.getSheetAt(0); //TODO: more sheets?
        setCols(worksheet);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { //first row is maybe a "header"
            XSSFRow row = worksheet.getRow(i);
            if (isValidRow(row)) {
                parsedList.add(new ExcelFileLoader(row));
            }
        }
        return parsedList;
    }

    private static void setCols(XSSFSheet worksheet) {
        XSSFRow row = worksheet.getRow(0);
        int i = 0;
        while (row.getCell(i) != null) {
            switch (row.getCell(i).getStringCellValue()) { //TODO: header check
                case "name":
                    name_col = i;
                    mandatory_cols.add(i);
                    break;
                case "zipcode":
                    zipcode_col = i;
                    mandatory_cols.add(i);
                    break;
                case "city":
                    city_col = i;
                    mandatory_cols.add(i);
                    break;
                case "address":
                    address_col = i;
                    mandatory_cols.add(i);
                    break;
                case "phone":
                    phone_col = i;
                    break;
                case "email":
                    email_col = i;
                    mandatory_cols.add(i);
                    break;
                case "website":
                    website_col = i;
                    break;
                case "description":
                    description_col = i;
                    mandatory_cols.add(i);
                    break;
                default:
            }
            i++;
        }
    }

    private static boolean isValidRow(XSSFRow row) {

        return mandatory_cols.stream()
                .allMatch((index -> isValidCell(row.getCell(index))));
    }

    private static boolean isValidCell(XSSFCell cell) {
        if (cell == null) {
            return false;
        } else {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            return !(cell.getStringCellValue().isEmpty()
                    || cell.getStringCellValue().isBlank());
        }
    }


    public String getName() {
        return name;
    }

    public Integer getZipcode() {
        return zipcode;
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

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }
}
