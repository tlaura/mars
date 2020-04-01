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

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "provider_email", referencedColumnName = "email", insertable = false, updatable = false)
    private User provider;

    @OneToMany(mappedBy = "contact")
    private List<Message> messages;

    public Contact(User user, User provider) {
        this.user = user;
        this.provider = provider;
    }
}
