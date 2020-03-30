package com.progmasters.mars.account_institution.institution.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class TempInstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotEmpty
    @Column(unique = true, name = "name")
    private String name;

    @PositiveOrZero
    @Column(name = "zip_code")
    private Integer zipcode;

    @NotBlank
    @NotEmpty
    @Column(name = "city")
    private String city;

    @NotBlank
    @NotEmpty
    @Column(name = "address")
    private String address;

    @NotBlank
    @NotEmpty
    @Email
    @Column(unique = true, name = "email")
    private String email;

    @Lob
    @NotBlank
    @Length(min = 30, max = 200)
    @Column(name = "description")
    private String description;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;
}
