package com.progmasters.mars.repository;

import com.progmasters.mars.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {


    List<Institution> findAllByName(@NotBlank @NotEmpty String name);

    List<Institution> findAllByEmail(@NotBlank @NotEmpty @Email String email);
}
