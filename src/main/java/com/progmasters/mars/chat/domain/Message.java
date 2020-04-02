package com.progmasters.mars.chat.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Contact contact;

    @Column(name = "message_sent_time")
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "text")
    private String text;

    @Column(name = "proposing_name")
    private String proposingName;

    @Column(name = "proposing_email")
    private String proposingEmail;

    @Column(name = "reciever_name")
    private String recieverName;

    @Column(name = "reciever_email")
    private String recieverEmail;

    public Message(String text, String proposingName, String proposingEmail, String recieverName, String recieverEmail) {
        this.text = text;
        this.proposingName = proposingName;
        this.proposingEmail = proposingEmail;
        this.recieverName = recieverName;
        this.recieverEmail = recieverEmail;
    }
}
