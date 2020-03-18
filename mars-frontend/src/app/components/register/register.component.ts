import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";
import {ProviderAccountRegisterModel} from "../../models/providerAccountRegisterModel";
import {validationHandler} from "../../utils/validationHandler";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  isNormalUser: boolean = true;
  haveProviderCustomAddress: boolean = false;
  registerForm: FormGroup;

  constructor(private accountService: AccountService, private router: Router) {
    this.isNormalUser = true;
    this.registerForm = new FormGroup(
      {
        'providerServiceName': new FormControl('', Validators.required),
        'password': new FormControl('', Validators.required),
        'ageGroupMin': new FormControl(0),
        'ageGroupMax': new FormControl(99),
        'types': new FormControl(null),

        'name': new FormControl('', (Validators.required)),
        'email': new FormControl('', Validators.required),
        'phone': new FormControl(''),
        'website': new FormControl(''),

        'zipcode': new FormControl(null),
        'city': new FormControl(null),
        'address': new FormControl(null),

        'institutions': new FormArray([]),

        'newsletter': new FormControl(false),
        'termsAndConditions': new FormControl(false, Validators.requiredTrue)
      }
    )
  }

  ngOnInit() {

  }

  submit() {
    if (!this.isNormalUser) {
      this.registerForm.markAllAsTouched();
      const formData: ProviderAccountRegisterModel = this.registerForm.value;
      this.accountService.saveProviderAccount(formData).subscribe(
        () => {
          this.router.navigate(['registration-complete']);
        },
        error => validationHandler(error, this.registerForm)
      );
    }
  }


  removeInstitution() {

  }

  addNewInstitution() {
    (this.registerForm.get('institutions') as FormArray).push();
  }


  chooseProviderUser() {
    this.isNormalUser = false;
  }

  chooseNormalUser() {
    this.isNormalUser = true;
  }

  changeProviderAddress() {
    this.haveProviderCustomAddress = !this.haveProviderCustomAddress;
    if (!this.haveProviderCustomAddress) {
      this.registerForm.get('zipcode').reset();
      this.registerForm.get('city').reset();
      this.registerForm.get('address').reset();
    }
  }

  setEmail(email: string) {
    this.registerForm.get('email').setValue(email);
  }

  setPhone(phone: string) {
    this.registerForm.get('phone').setValue(phone);
  }

  setWebsite(website: string) {
    this.registerForm.get('website').setValue(website);
  }

  setName(name: string) {
    this.registerForm.get('name').setValue(name);
  }

  setZipcode(zipcode: number) {
    this.registerForm.get('zipcode').setValue(zipcode);
  }

  setCity(city: string) {
    this.registerForm.get('city').setValue(city);
  }

  setAddress(address: string) {
    this.registerForm.get('address').setValue(address);
  }

  setPassword(password: string) {
    this.registerForm.get('password').setValue(password);
  }

  setProviderServiceName(providerServiceName: string) {
    this.registerForm.get('providerServiceName').setValue(providerServiceName);
  }

  setAgeGroupMax(ageGroupMax: string) {
    this.registerForm.get('ageGroupMax').setValue(ageGroupMax);
  }

  setAgeGroupMin(ageGroupMin: string) {
    this.registerForm.get('ageGroupMin').setValue(ageGroupMin);
  }

  setTypes(types: string[]) {
    this.registerForm.get('types').setValue(types);
  }

}
