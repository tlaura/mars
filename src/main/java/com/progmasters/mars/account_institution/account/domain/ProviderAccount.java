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
public class ProviderAccount extends User {

    @NotBlank
    @NotEmpty
    @Column(name = "provider_service_name")
    private String providerServiceName;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "provider_account_type", joinColumns = @JoinColumn(name = "provider_account_id"))
    private List<ProviderType> types;

    @PositiveOrZero
    @Column(name = "age_group_min")
    private Integer ageGroupMin;

    @PositiveOrZero
    @Column(name = "age_group_max")
    private Integer ageGroupMax;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "providerAccount")
    private List<AccountInstitutionConnector> accountInstitutionConnectors;

    public ProviderAccount(ProviderAccountCreationCommand providerAccountCreationCommand) {
        this.providerServiceName = providerAccountCreationCommand.getProviderServiceName();
        super.setPassword(providerAccountCreationCommand.getPassword());
        super.setName(providerAccountCreationCommand.getName());
        super.setZipcode(providerAccountCreationCommand.getZipcode());
        super.setCity(providerAccountCreationCommand.getCity());
        super.setAddress(providerAccountCreationCommand.getAddress());
        super.setEmail(providerAccountCreationCommand.getEmail());
        super.setPhone(providerAccountCreationCommand.getPhone());
        this.ageGroupMin = providerAccountCreationCommand.getAgeGroupMin();
        this.ageGroupMax = providerAccountCreationCommand.getAgeGroupMax();
        this.types = providerAccountCreationCommand.getTypes().stream().map(ProviderType::getTypeByHungarianName).collect(Collectors.toList());
        super.setNewsletter(providerAccountCreationCommand.getNewsletter());
        super.setRole(Role.ROLE_PROVIDER);
    }

}
