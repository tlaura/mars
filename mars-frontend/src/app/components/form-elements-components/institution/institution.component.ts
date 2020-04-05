import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {InstitutionListModel} from "../../../account-institution/institution/models/institutionList.model";
import {InstitutionService} from "../../../account-institution/institution/services/institution.service";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

  @Input()
  institutionForm: FormGroup;

  selectedInstitution: boolean = false;

  institutionList: Array<InstitutionListModel>;

  institutionModel: InstitutionListModel;


  constructor(private institutionService: InstitutionService) {
    this.institutionForm = new FormGroup({});

  }

  ngOnInit(): void {
    this.getAllInstitutions();
  }


  getAllInstitutions = (): void => {
    this.institutionService.getAllInstitutions().subscribe(
      value => this.institutionList = value,
      error => console.warn(error)
    );
  };


  getOpeningHours() {
    return (this.institutionForm.get('openingHours') as FormArray);
  }

  addNewOpeningHours() {
    if (this.getOpeningHours().length < 15) {
      (this.institutionForm.get('openingHours') as FormArray).push(new FormGroup({
        'weekDay': new FormControl(null),
        'openingTime': new FormControl(null),
        'closingTime': new FormControl(null)
      }));
    }
  }

  removeOpeningHours(index: number) {
    (this.institutionForm.get('openingHours') as FormArray).removeAt(index);
  };


  fillFields = (event): void => {
    let index: number = event.target.value;
    if (index != -1) {
      this.selectedInstitution = true;
      this.institutionForm.disable();
      this.institutionModel = this.institutionList[index];
    } else {
      this.institutionForm.enable();
      this.selectedInstitution = false;
    }
  };
}
