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

    public Message(String text) {
        this.text = text;
    }
}
