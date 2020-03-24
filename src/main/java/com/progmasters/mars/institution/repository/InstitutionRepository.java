package com.progmasters.mars.institution.repository;

import com.progmasters.mars.institution.domain.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {


    List<Institution> findAllByName(@NotBlank @NotEmpty String name);

    List<Institution> findAllByEmail(@NotBlank @NotEmpty @Email String email);

    Institution findByName(String name);

    @Query("select i from Institution i where i.providerAccount= 'null'")
    Page<Institution> findAllWithoutAccount(Pageable pageable);
}
