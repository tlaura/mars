package com.progmasters.mars.account_institution.account.domain;

import com.progmasters.mars.account_institution.account.dto.ProviderAccountCreationCommand;
import com.progmasters.mars.account_institution.connector.AccountInstitutionConnector;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "provider_account")
@Getter
@Setter
@NoArgsConstructor
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
    @CollectionTable(name = "provider_account_type", joinColumns = @JoinColumn(name = "provider_account_id"))
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

}
