package com.progmasters.mars.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "confirmation_token")
    private String token = UUID.randomUUID().toString();

    @Column(name = "creation_date")
    private LocalDateTime date =LocalDateTime.now();;

    @JoinColumn(name = "user_id")
    @OneToOne
    private IndividualUser individualUser;

    @JoinColumn(name = "institution_id")
    @OneToOne
    private InstitutionalUser institutionalUser;

    public ConfirmationToken() {
    }

    public ConfirmationToken(IndividualUser individualUser) {
        this.individualUser = individualUser;
    }

    public ConfirmationToken(InstitutionalUser institutionalUser)
    {
        this.institutionalUser=institutionalUser;
    }

    //----------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public IndividualUser getIndividualUser() {
        return individualUser;
    }

    public void setIndividualUser(IndividualUser user) {
        this.individualUser = user;
    }

    public InstitutionalUser getInstitutionalUser() {
        return institutionalUser;
    }

    public void setInstitutionalUser(InstitutionalUser institutionalUser) {
        this.institutionalUser = institutionalUser;
    }
}
