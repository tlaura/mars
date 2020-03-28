package com.progmasters.mars.account_institution.account.passwordtoken;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "password_token")
@Getter
@Setter
@NoArgsConstructor
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

    public PasswordToken(ProviderAccount user) {
        this.user = user;
    }
}
