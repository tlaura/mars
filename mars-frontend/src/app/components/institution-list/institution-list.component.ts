import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../services/institution.service";
import {Router} from "@angular/router";
import {InstitutionListModel} from "../../models/institutionList.model";
import {InstitutionTypeModel} from "../../models/InstitutionType.model";
import {institutionListIndex} from "../../../environments/institutionListIndex.prod";
import {AccountService} from "../../services/account.service";

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

  sizeArray: Array<number> = institutionListIndex.itemsPerPageArray;

  constructor(private institutionService: InstitutionService,
              private accountService: AccountService,
              private router: Router) {
  }

  setSize = (size: number) => {
    this.size = size;
    this.page = institutionListIndex.startPageIndex;
    this.getInstitutions();
  };

  ngOnInit() {
    this.getInstitutions();
    this.getInstitutionType();
  }

  narrowByType = (type: string) => {
    //todo refactor magic string
    if (type !== "all") {
      this.getInstitutionsByType(type);
    } else {
      this.getInstitutions();
    }
  };

  private getInstitutionType = () => {
    this.institutionService.getInstitutionTypes().subscribe(
      institutionTypeList => this.institutionTypeList = institutionTypeList,
      error => console.warn(error)
    );
  };

  private getInstitutionsByType = (type: string) => {
    this.accountService.getInstitutionByType(type).subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
    );
  };

  private getInstitutions = () => {
    this.institutionService.getInstitutionList().subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error),
      () => console.log(this.institutionList)
    );
  };


  details = (id: number) => {
    this.router.navigate(["institution-details", id]);
  };
}
