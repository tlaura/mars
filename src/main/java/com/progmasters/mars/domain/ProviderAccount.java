package com.progmasters.mars.domain;

import com.progmasters.mars.dto.ProviderAccountCreationCommand;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @NotNull
    @Size(min = 1)
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "provicer_account_type", joinColumns = @JoinColumn(name = "provider_account_id"))
    private List<InstitutionType> types;

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

    @OneToMany(mappedBy = "providerAccount")
    private List<Institution> institutions;


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
        this.types = providerAccountCreationCommand.getTypes().stream().map(InstitutionType::getTypeByHungarianName).collect(Collectors.toList());
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

    public List<InstitutionType> getTypes() {
        return types;
    }

    public void setTypes(List<InstitutionType> type) {
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

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }
}
