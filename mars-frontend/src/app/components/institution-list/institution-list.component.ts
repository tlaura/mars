import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../services/institution.service";
import {ActivatedRoute, Router} from "@angular/router";
import {InstitutionListModel} from "../../models/institutionList.model";
import {InstitutionTypeModel} from "../../models/InstitutionType.model";
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {AccountService} from "../../services/account.service";
import {SocialService} from "ngx-social-button";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-institution-list',
  templateUrl: './institution-list.component.html',
  styleUrls: ['./institution-list.component.css']
})
export class InstitutionListComponent implements OnInit {

  institutionList: Array<InstitutionListModel>;
  institutionTypeList: Array<InstitutionTypeModel>;
  page: number = institutionListIndex.startPageIndex;
  size: number = institutionListIndex.numberOfItemPerPage;
  searchText: string;
  currentType: string;
  institutionType: FormControl;
  showWindows: boolean;

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
              private socialService: SocialService) {
    this.institutionType = new FormControl('all');
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
    this.getInstitutionType();
  }

  loadPageByFilterType = (type: string, result: string): void => {
    switch (type) {
      case "providerType":
        this.getInstitutionsByType(result);
        break;
      case "search":
        //todo
        break;
    }
  };

  narrowByType = (type: string) => {
    //todo refactor magic string
    if (type !== "all") {
      this.getInstitutionsByType(type);
    } else {
      this.getInstitutions();
    }
    this.showWindows = false;

  };

  private getInstitutionType = () => {
    this.institutionService.getInstitutionTypes().subscribe(
      institutionTypeList => this.institutionTypeList = institutionTypeList,
      error => console.warn(error)
    );
  };

  shareList() {
    if (this.currentType != "all") {
      this.shareObj.href = "http://localhost:4200/institution-list/providerType/" + this.currentType;
    }
    this.socialService.facebookSharing(this.shareObj);
  }

  private getInstitutions = () => {
    this.institutionService.getInstitutionList().subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
    );
  };


  details = (id: number) => {
    this.router.navigate(["institution-details", id]);
  };

  setCurrentType(type: string) {
    this.currentType = type;
  }

  private getInstitutionsByType = (type: string) => {
    this.accountService.getInstitutionByType(type).subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
    );
  };
}
