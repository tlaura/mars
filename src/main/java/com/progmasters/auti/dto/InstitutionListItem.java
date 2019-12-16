package com.progmasters.auti.dto;

import com.progmasters.auti.domain.Institution;

public class InstitutionListItem {

    private Long id;
    private String name;

    public InstitutionListItem() {
    }

    public InstitutionListItem(Institution institution) {
        this.id = institution.getId();
        this.name = institution.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
