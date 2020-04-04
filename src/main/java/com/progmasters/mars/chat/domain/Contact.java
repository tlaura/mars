package com.progmasters.mars.chat.domain;

import com.progmasters.mars.account_institution.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User fromAccount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provider_email", referencedColumnName = "email")
    private User toAccount;

    @OneToMany(mappedBy = "contact")
    private List<Message> messages;

    public Contact(User fromAccount, User toAccount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }
}
