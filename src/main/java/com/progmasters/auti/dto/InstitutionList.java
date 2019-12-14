package com.progmasters.auti.dto;

import com.progmasters.auti.domain.Institution;

import java.util.List;

public class InstitutionList {
    private List<InstitutionListItem> institutions;

    public InstitutionList(List<InstitutionListItem> institutions) {
        this.institutions = institutions;
    }

    public List<InstitutionListItem> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<InstitutionListItem> institutions) {
        this.institutions = institutions;
    }
}
