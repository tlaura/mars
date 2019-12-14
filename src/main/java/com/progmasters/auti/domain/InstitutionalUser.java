package com.progmasters.auti.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class InstitutionalUser extends User {

    @OneToMany(mappedBy = "creator")
    private List<Institution> institutions;

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }
}
