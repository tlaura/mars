package com.progmasters.mars.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProviderAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String zipcode;
    private String city;
    private String address;
    private String type; //TODO: enum
    @OneToMany
    private List<OpeningHours> openingHours;
    private Integer ageGroupMin;
    private Integer ageGroupMax;
    @OneToMany
    private List<Institution> institutions;
    private Boolean newsletter;
}
