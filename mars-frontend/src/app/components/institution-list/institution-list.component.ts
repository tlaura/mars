import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../services/institution.service";
import {Router} from "@angular/router";
import {InstitutionListModel} from "../../models/institutionList.model";
import {InstitutionTypeModel} from "../../models/InstitutionType.model";

@Component({
  selector: 'app-institution-list',
  templateUrl: './institution-list.component.html',
  styleUrls: ['./institution-list.component.css']
})
export class InstitutionListComponent implements OnInit {

  institutionList: Array<InstitutionListModel>;
  institutionTypeList: Array<InstitutionTypeModel>;
  searchText: string;
  page: number = 1;
  size: number = 10;

  sizeArray: Array<number> = [10, 50, 100];

  constructor(private institutionService: InstitutionService,
              private router: Router) {
  }

  setSize = (size: number) => {
    this.size = size;
    this.page = 1;
    this.getInstitutions();
  };

  ngOnInit() {
    this.getInstitutions();
    this.getInstitutionType();
  }

  narrowByType = (type: string) => {
    if (type !== "all") {
      this.institutionService.getInstitutionByType(type).subscribe(
        institutionList => this.institutionList = institutionList,
        error => console.warn(error)
      );
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

  private getInstitutions = () => {
    this.institutionService.getInstitutionList().subscribe(
      institutionList => this.institutionList = institutionList,
      error => console.warn(error)
    );
  };

  details = (id: number) => {
    this.router.navigate(["institution-details", id]);
  };
}
