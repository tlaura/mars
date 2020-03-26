import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../../services/institution.service";
import {ActivatedRoute, Router} from "@angular/router";
import {InstitutionListModel} from "../../../models/institutionList.model";
import {InstitutionTypeModel} from "../../../models/InstitutionType.model";
import {institutionListIndex} from "../../../../environments/institutionListIndex.prod";
import {AccountService} from "../../../services/account.service";
import {SocialService} from "ngx-social-button";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountInstitutionConnectorService} from "../../../services/account-institution-connector.service";
import {AccountInstitutionListModel} from "../../../models/accountInstitutionList.model";

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
        ageGroupMin: new FormControl(0, Validators.required),
        ageGroupMax: new FormControl(0, Validators.required)
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
          const filterResult: string = param.get('filterResult');
          this.loadPageByFilterType(filterType, filterResult);
          this.institutionType.setValue(filterType);
        } else {
          this.getInstitutions();
        }
      },
    );
    this.getProviderType();
  }

  loadPageByFilterType = (type: string, result: string): void => {
    switch (type) {
      case "providerType":
        //   this.getInstitutionsByType(result);
        break;
      case "search":
        //todo
        break;
    }
  };


  narrowByAgeRange = (): void => {
    if (this.ageRangeGroup.valid) {
      let min: number = this.ageRangeGroup.get('min').value;
      let max: number = this.ageRangeGroup.get('max').value;
      this.accountService.getProviderAccountsByAgeRange(min, max).subscribe(
        value => this.institutionList = value,
        error => console.warn(error),
        () => this.showWindows = false
      );
    }

  };

  narrowByType = (type: string) => {
    //todo refactor magic string
    if (type !== "all") {
      this.getProvidersByType(type);
    } else {
      this.getInstitutions();
    }
  };

  details = (index: number): string => {

    let id = this.institutionList[index].id;
    let type: string = this.institutionList[index].accountType;
    switch (type) {
      case 'PROVIDER':
        return "provider-details/" + id;
      case 'INSTITUTION':
        return "institution-details/" + id;
      case 'INSTITUTION_WITH_PROVIDER':
        //todo
        return "institution-details/" + id;
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

  private getProviderType = () => {
    this.institutionService.getProviderTypes().subscribe(
      institutionTypeList => this.institutionTypeList = institutionTypeList,
      error => console.warn(error)
    );
  };

  setCurrentType(type: string) {
    this.currentType = type;
  }

  private getInstitutions = () => {
    this.accountInstitutionService.getAllAccountConnections().subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
      () => this.showWindows = false
    );
  };

  filteredInstitutionList: Array<InstitutionListModel> = new Array<InstitutionListModel>();

  // createFilteredList() {
  //   debugger;
  //   this.filteredInstitutionList = this.institutionList.filter(institution => {
  //     for (let key in institution) {
  //       if (institution[key].includes(this.searchText)) {
  //         return institution[key];
  //       }
  //     }
  //   });
  //   debugger;
  // }

  isProvider(institution: AccountInstitutionListModel): boolean {
    return institution.accountType === 'PROVIDER';
  }

  hasProviders(institution: AccountInstitutionListModel): boolean {
    //TODO: length->provider list length...
    return institution.providerServiceName?.length > 0 && institution.accountType != 'PROVIDER';
  }

  private getProvidersByType = (type: string) => {
    this.accountService.getProvidersByType(type).subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
      () => this.showWindows = false
    );
  };
}
