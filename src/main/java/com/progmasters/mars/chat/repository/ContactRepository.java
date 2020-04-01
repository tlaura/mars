package com.progmasters.mars.chat.repository;

import com.progmasters.mars.chat.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where (c.fromUser.email= :fromEmail and c.toUser.email= :toEmail) or (c.toUser.email= :fromEmail and c.fromUser.email= :toEmail)")
    ContactRepository findConnectionByUsers(@Param("fromEmail") String fromEmail, @Param("toEmail") String toEmail);
}
