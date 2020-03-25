package com.progmasters.mars.account_institution.account.domain;

import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.connector.AccountInstitutionConnector;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "provider_account")
public class ProviderAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotEmpty
    @Column(name = "provider_service_name")
    private String providerServiceName;

    @NotBlank
    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotBlank
    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotBlank
    @NotEmpty
    @Column(name = "email")
    private String email;

    //    @NotNull
//    @Size(min = 1)
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "provicer_account_type", joinColumns = @JoinColumn(name = "provider_account_id"))
    private List<ProviderType> types;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @PositiveOrZero
    @Column(name = "age_group_min")
    private Integer ageGroupMin;

    @PositiveOrZero
    @Column(name = "age_group_max")
    private Integer ageGroupMax;

    @Column(name = "newsletter")
    private Boolean newsletter;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "providerAccount")
    private List<AccountInstitutionConnector> accountInstitutionConnectors;


    public ProviderAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        this.providerServiceName = providerAccountCreationCommand.getProviderServiceName();
        this.name = providerAccountCreationCommand.getName();
        this.zipcode = providerAccountCreationCommand.getZipcode();
        this.city = providerAccountCreationCommand.getCity();
        this.address = providerAccountCreationCommand.getAddress();
        this.email = providerAccountCreationCommand.getEmail();
        this.phone = providerAccountCreationCommand.getPhone();
        this.ageGroupMin = providerAccountCreationCommand.getAgeGroupMin();
        this.ageGroupMax = providerAccountCreationCommand.getAgeGroupMax();
        this.types = providerAccountCreationCommand.getTypes().stream().map(ProviderType::getTypeByHungarianName).collect(Collectors.toList());
        this.newsletter = providerAccountCreationCommand.getNewsletter();
        this.role = Role.ROLE_PROVIDER;
    }

    public ProviderAccount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public void setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProviderType> getTypes() {
        return types;
    }

    public void setTypes(List<ProviderType> type) {
        this.types = type;
    }

    public Integer getAgeGroupMin() {
        return ageGroupMin;
    }

    public void setAgeGroupMin(Integer ageGroupMin) {
        this.ageGroupMin = ageGroupMin;
    }

    public Integer getAgeGroupMax() {
        return ageGroupMax;
    }

    public void setAgeGroupMax(Integer ageGroupMax) {
        this.ageGroupMax = ageGroupMax;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public List<AccountInstitutionConnector> getAccountInstitutionConnectors() {
        return accountInstitutionConnectors;
    }

    public void setAccountInstitutionConnectors(List<AccountInstitutionConnector> accountInstitutionConnectors) {
        this.accountInstitutionConnectors = accountInstitutionConnectors;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
