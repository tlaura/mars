package com.progmasters.mars.chat.repository;

import com.progmasters.mars.account_institution.account.domain.User;
import com.progmasters.mars.chat.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where (c.fromAccount.email= :fromEmail and c.toAccount.email= :toEmail) or (c.toAccount.email= :fromEmail and c.fromAccount.email= :toEmail)")
    Contact findConnectionByUsers(@Param("fromEmail") String fromEmail, @Param("toEmail") String toEmail);

    @Query("select c from Contact c where c.fromAccount= :fromAccount and c.toAccount= :toAccount")
    List<Contact> findMultipleContacts(@Param("fromAccount") User fromAccount, @Param("toAccount") User toAccount);

}
