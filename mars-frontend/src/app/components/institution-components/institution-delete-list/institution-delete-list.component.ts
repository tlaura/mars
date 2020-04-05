import {Component, OnInit} from '@angular/core';
import {InstitutionService} from "../../../account-institution/institution/services/institution.service";
import {InstitutionDeleteListModel} from "../../../account-institution/institution/models/institutionDeleteList.model";

@Component({
  selector: 'app-institution-delete-list',
  templateUrl: './institution-delete-list.component.html',
  styleUrls: ['./institution-delete-list.component.css']
})
export class InstitutionDeleteListComponent implements OnInit {

  deleteList: Array<InstitutionDeleteListModel>;

  constructor(private institutionService: InstitutionService) {
  }

  ngOnInit(): void {
    this.getDeleteInstitutionList();
  }

  getDeleteInstitutionList = (): void => {
    this.institutionService.getDeleteInstitutionList().subscribe(
      value => this.deleteList = value,
      error => console.warn(error),
    );
  };

  delete(id: number): void {
    this.institutionService.deleteInstitution(id).subscribe(
      () => this.getDeleteInstitutionList(),
      error => console.warn(error)
    );
  };

  reject(id: number): void {
    this.institutionService.rejectInstitutionDelete(id).subscribe(
      () => this.getDeleteInstitutionList(),
      error => console.warn(error)
    );
  };
}
