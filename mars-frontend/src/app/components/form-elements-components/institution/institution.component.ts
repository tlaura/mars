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
  @Input()
  selectable: boolean = false;

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
      this.institutionModel = this.institutionList[index];

      this.institutionForm.get('zipcode').setValue(this.institutionModel.zipcode);
      this.institutionForm.get('city').setValue(this.institutionModel.city);
      this.institutionForm.get('address').setValue(this.institutionModel.address);
      this.institutionForm.get('name').setValue(this.institutionModel.name);
      this.institutionForm.get('email').setValue(this.institutionModel.email);
      this.institutionForm.get('phone').setValue(this.institutionModel.phone);
      this.institutionForm.get('website').setValue(this.institutionModel.website);
      this.institutionForm.get('description').setValue(this.institutionModel.description);

      this.institutionForm.disable();
      this.institutionForm.clearValidators();
      this.institutionForm.updateValueAndValidity();
    } else {
      this.institutionForm.enable();
      this.institutionForm.updateValueAndValidity();
      this.selectedInstitution = false;
    }
  };
}
