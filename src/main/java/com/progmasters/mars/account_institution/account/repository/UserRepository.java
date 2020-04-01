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

    @Query("select p from User u join u.providers pList join pList.provider p  where u.email= :email")
    List<User> findUsersByContactEmail(@Param("email") String email);
}
