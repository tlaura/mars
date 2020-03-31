package com.progmasters.mars.account_institution.account.domain;

import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @Column(name = "newsletter")
    private Boolean newsletter;

    public User(UserCreationCommand userCreationCommand) {
        this.name = userCreationCommand.getName();
        this.password = userCreationCommand.getPassword();
        this.email = userCreationCommand.getEmail();
        this.phone = userCreationCommand.getPhone();
        this.role = Role.ROLE_IND;
        this.zipcode = userCreationCommand.getZipcode();
        this.city = userCreationCommand.getCity();
        this.address = userCreationCommand.getAddress();
        this.newsletter = userCreationCommand.getNewsletter();
    }
}
