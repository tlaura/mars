package com.progmasters.mars.account_institution.account.passwordtoken;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_token")
public class PasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "password_token")
    private String token = UUID.randomUUID().toString();

    //TODO Ez itt lehetne még szebb, ha ZonedDateTime mentődne a DB-be,
    // erről majd írok valamit, mert elég szívatós dolog
    @Column(name = "creation_date")
    private LocalDateTime date = LocalDateTime.now();

    @JoinColumn(name = "user_id")
    @OneToOne
    private ProviderAccount user;

    public PasswordToken() {
    }

    public PasswordToken(ProviderAccount user) {
        this.user = user;
    }

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

    public ProviderAccount getUser() {
        return user;
    }

    public void setUser(ProviderAccount user) {
        this.user = user;
    }
}
