package com.progmasters.mars.account_institution.account.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class IndividualUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Length(min = 6, max = 32)
    private String userName;

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
