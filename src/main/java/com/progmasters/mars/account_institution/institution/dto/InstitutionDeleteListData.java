package com.progmasters.mars.account_institution.institution.dto;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InstitutionDeleteListData {

    private List<String> causes;

    private InstitutionListData institutionListData;

    public InstitutionDeleteListData(Institution institution) {
        this.causes = institution.getInstitutionPetition().getCauses();
        this.institutionListData = new InstitutionListData(institution);
    }

}
