package com.progmasters.mars.account_institution.account.domain;

import com.progmasters.mars.account_institution.account.dto.UserCreationCommand;
import com.progmasters.mars.chat.domain.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
    @Column(name = "email", unique = true)
    protected String email;

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

    @OneToMany(mappedBy = "fromAccount")
    private List<Contact> fromAccount;

    @OneToMany(mappedBy = "toAccount")
    private List<Contact> toAccount;

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
