package com.progmasters.mars.account_institution.account.confirmationtoken;

import com.progmasters.mars.account_institution.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmation_token")
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "confirmation_token")
    private String token = UUID.randomUUID().toString();

    //TODO Ez itt lehetne még szebb, ha ZonedDateTime mentődne a DB-be,
    // erről majd írok valamit, mert elég szívatós dolog
    @Column(name = "creation_date")
    private LocalDateTime date = LocalDateTime.now();

    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    private boolean confirmed = false;

    public ConfirmationToken(User user) {
        this.user = user;
    }

    public boolean isConfirmed() {
        return confirmed;
    }


}
