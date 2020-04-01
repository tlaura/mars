package com.progmasters.mars.account_institution.account.repository;

import com.progmasters.mars.account_institution.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByEmail(String email);

    @Query("select tUser from User u join u.fromAccount fAccount join fAccount.toAccount tUser  where u.email= :email")
    List<User> findRecievingUsersByEmail(@Param("email") String email);

    @Query("select fUser from User u join u.toAccount tAccount join tAccount.fromAccount fUser where u.email= :email")
    List<User> findProposingUserByEmail(@Param("email") String email);

    @Query("select u from User u join u.fromAccount fromA join fromA.toAccount toA where u.email= :fromEmail and toA.email= :toEmail")
    List<User> findIfExistByEmail(@Param("fromEmail") String fromEmail, @Param("toEmail") String toEmail);
}
