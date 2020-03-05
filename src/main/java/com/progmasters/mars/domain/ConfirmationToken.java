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
    private String token;

    @Column(name = "creation_date")
    private LocalDateTime date;

    @JoinColumn(name = "user_id")
    @OneToOne
    private IndividualUser user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(IndividualUser individualUser) {
        this.user = individualUser;
        this.date = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }
}
