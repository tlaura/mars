import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../../services/institution.service";
import {ActivatedRoute, Router} from "@angular/router";
import {InstitutionTypeModel} from "../../../models/InstitutionType.model";
import {institutionListIndex} from "../../../../environments/institutionListIndex.prod";
import {AccountService} from "../../../services/account.service";
import {SocialService} from "ngx-social-button";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountInstitutionConnectorService} from "../../../services/account-institution-connector.service";
import {AccountInstitutionListModel} from "../../../models/accountInstitutionList.model";
import {LocationRangeModel} from "../../../models/locationRange.model";

@Component({
    selector: 'app-institution-list',
    templateUrl: './institution-list.component.html',
    styleUrls: ['./institution-list.component.css']
})
export class InstitutionListComponent implements OnInit {

    institutionList: Array<AccountInstitutionListModel>;
    institutionTypeList: Array<InstitutionTypeModel>;
    page: number = institutionListIndex.startPageIndex;
    size: number = institutionListIndex.numberOfItemPerPage;
    searchText: string;
    currentType: string;
    institutionType: FormControl;
    showWindows: boolean;
    ageRangeGroup: FormGroup;
    test: number = 5;
    age: number = 0;
    locationRange: LocationRangeModel;

    shareObj = {
        //TODO: localhost-ra nem működik, elvileg élesben igen?!?!?
        href: "",
        hashtag: "#NONE"
    };

    sizeArray: Array<number> = institutionListIndex.itemsPerPageArray;

    constructor(private institutionService: InstitutionService,
                private accountService: AccountService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private socialService: SocialService,
                private accountInstitutionService: AccountInstitutionConnectorService,
                private formBuilder: FormBuilder) {
        this.institutionType = new FormControl('all');
        this.ageRangeGroup = formBuilder.group({
                age: new FormControl(0, Validators.required)
            }
        )
    }


    setSize = (size: number) => {
        this.size = size;
        this.page = institutionListIndex.startPageIndex;
        this.getInstitutions();
    };

    ngOnInit() {
        this.activatedRoute.paramMap.subscribe(
            param => {
                const filterType: string = param.get('filterType');
                if (filterType) {
                    this.institutionType.setValue(filterType);
                } else {
                    this.getInstitutions();
                }
            },
        );
        this.getProviderType();

    }

    narrowByType = (type: string) => {
        //todo refactor magic string
        if (type !== "all") {
            this.getProvidersByType(type);
        } else {
            this.getInstitutions();
        }
    };

    details = (institution: AccountInstitutionListModel): string => {
        let type: string = institution.accountType;
        if (type === 'PROVIDER') {
            return "provider-details/" + institution.id;
        } else {
            return "institution-details/" + institution.id;
        }
    };

    shareList() {
        if (this.currentType != "all") {
            this.shareObj.href = "http://localhost:4200/institution-list/providerType/" + this.currentType;
        }
        this.socialService.facebookSharing(this.shareObj);
    }

    getAgesArray(n: number): Array<number> {
        return Array(n);
    }

    setCurrentType(type: string) {
        this.currentType = type;
    }

    isProvider(institution: AccountInstitutionListModel): boolean {
        return institution.accountType === 'PROVIDER';
    }

    hasProviders(institution: AccountInstitutionListModel): boolean {
        return institution.providers?.length > 0 && institution.accountType != 'PROVIDER';
    }

    setRange = (range: number): void => {
        if (range) {
            this.locationRange.range = range;
            this.accountInstitutionService.getAccountsByRange(this.locationRange).subscribe(
                value => this.institutionList = value,
                error => console.warn(error)
            );
        }
    };

    setCurrentPosition = (locationRange: LocationRangeModel): void => {
        this.locationRange = locationRange;
    };

    reset = (): void => {
        this.getInstitutions();
    };

    setAge = (age: number): void => {
        if (age) {
            this.accountService.getProviderAccountsByAgeRange(age).subscribe(
                value => this.institutionList = value,
                error => console.warn(error),
                () => this.showWindows = false
            );
        }
    }

    private getProviderType = () => {
        this.institutionService.getProviderTypes().subscribe(
            institutionTypeList => this.institutionTypeList = institutionTypeList,
            error => console.warn(error)
        );
    };

    private getInstitutions = () => {
        this.accountInstitutionService.getAllAccountConnections().subscribe(
            institutionList => this.institutionList = institutionList,
            error => console.warn(error),
            () => this.showWindows = false
        );
    };

    private getProvidersByType = (type: string) => {
        this.accountService.getProvidersByType(type).subscribe(
            institutionList => this.institutionList = institutionList,
            error => console.warn(error),
            () => this.showWindows = false
        );
    };
}
