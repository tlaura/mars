import {Component, OnInit} from '@angular/core';
import {AbstractControl, Form, FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Time} from "@angular/common";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

  institutions: FormGroup;

  constructor() {
    this.institutions = new FormGroup({
      'zipcode': new FormControl(null, Validators.required),
      'city': new FormControl(null, Validators.required),
      'address': new FormControl(null, Validators.required),

      'name': new FormControl('', Validators.required),
      'email': new FormControl('', Validators.required),
      'phone': new FormControl(),
      'website': new FormControl(),

      'description': new FormControl('',
        [Validators.required, Validators.minLength(30), Validators.maxLength(200)]),

      'openingHours': new FormArray([])
    })
  }

  ngOnInit(): void {
  }

  getOpeningHours(){
    return this.institutions.get('openingHours') as FormArray;
  }

  setWeekDay(weekDay: string, openingHoursGroup: AbstractControl) {
    (openingHoursGroup as FormGroup).setControl('weekDay', new FormControl(weekDay));
  }

  setOpeningTime(openingTime: Time, openingHoursGroup: AbstractControl) {
    (openingHoursGroup as FormGroup).setControl('openingTime', new FormControl(openingTime));
  }

  setClosing(closingTime: Time, openingHoursGroup: AbstractControl) {
    (openingHoursGroup as FormGroup).setControl('closingTime', new FormControl(closingTime));
  }

  addNewOpeningHours() {
    (this.institutions.get('openingHours') as FormArray).push(new FormGroup({
      'weekDay': new FormControl(),
      'openingTime': new FormControl(),
      'closingTime': new FormControl()
    }));
  }

  removeOpeningHours(index: number) {
    (this.institutions.get('openingHours') as FormArray).removeAt(index);
  }
}
