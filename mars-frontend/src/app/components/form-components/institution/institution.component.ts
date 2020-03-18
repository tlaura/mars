import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-institution',
  templateUrl: './institution.component.html',
  styleUrls: ['./institution.component.css']
})
export class InstitutionComponent implements OnInit {

  @Input()
  institutionForm: FormGroup;

  constructor() {
    this.institutionForm = new FormGroup({
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
    return (this.institutionForm.get('openingHours') as FormArray);
  }

  addNewOpeningHours() {
    (this.institutionForm.get('openingHours') as FormArray).push(new FormGroup({
      'weekDay': new FormControl(null),
      'openingTime': new FormControl(null),
      'closingTime': new FormControl(null)
    }));
  }

  removeOpeningHours(index: number) {
    (this.institutionForm.get('openingHours') as FormArray).removeAt(index);
  }

  setEmail(email: string) {
    this.institutionForm.get('email').setValue(email);
  }

  setPhone(phone: string) {
    this.institutionForm.get('phone').setValue(phone);
  }

  setWebsite(website: string) {
    this.institutionForm.get('website').setValue(website);
  }

  setName(name: string) {
    this.institutionForm.get('name').setValue(name);
  }

  setZipcode(zipcode: number) {
    this.institutionForm.get('zipcode').setValue(zipcode);
  }

  setCity(city: string) {
    this.institutionForm.get('city').setValue(city);
  }

  setAddress(address: string) {
    this.institutionForm.get('address').setValue(address);
  }
}
