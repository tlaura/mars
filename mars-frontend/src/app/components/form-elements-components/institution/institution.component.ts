import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {InstitutionListModel} from "../../../account-institution/institution/models/institutionList.model";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

  @Input()
  institutionForm: FormGroup;

  @Input()
  selectedInstitution: boolean;

  @Input()
  institutionModel: InstitutionListModel;


  constructor() {
    this.institutionForm = new FormGroup({});

  }

  ngOnInit(): void {
  }


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
  }
}
