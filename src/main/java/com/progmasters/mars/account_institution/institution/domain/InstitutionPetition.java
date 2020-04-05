package com.progmasters.mars.account_institution.institution.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "institution_delete")
@NoArgsConstructor
@Getter
@Setter
public class InstitutionPetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cause_of_delete")
    @ElementCollection
    private List<String> causes = new ArrayList<>();

    @Column(name = "number_of_request")
    private Integer numberOfRequest = 1;

    @OneToOne
    private Institution institution;

    @Column(name = "signed_for_deletion")
    private Boolean deleteSign = false;

    public InstitutionPetition(String cause) {
        this.deleteSign = true;
        this.causes.add(cause);
    }
}
