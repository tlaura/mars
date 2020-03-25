import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../../services/account.service";
import {Router} from "@angular/router";
import {ProviderAccountRegisterModel} from "../../../../models/providerAccountRegisterModel";
import {validationHandler} from "../../../../utils/validationHandler";
import {validatorBounds} from "../../../../../environments/validatorBounds";
import {InstitutionTypeModel} from "../../../../models/InstitutionType.model";
import {InstitutionService} from "../../../../services/institution.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  isNormalUser: boolean = false;
  haveProviderCustomAddress: boolean = false;
  isPasswordValid: boolean = false;
  registerForm: FormGroup;
  addressFormGroup: FormGroup;

  allType: Array<InstitutionTypeModel> = [];

  constructor(private accountService: AccountService, private router: Router, private institutionService: InstitutionService) {
    this.chooseProviderUser();
    this.registerForm = new FormGroup(
      {
        'providerServiceName': new FormControl('', Validators.required),
        'password': new FormControl(''),
        'ageGroupMin': new FormControl(validatorBounds.ageGroupMin),
        'ageGroupMax': new FormControl(validatorBounds.ageGroupMax),
        'types': new FormControl(null, [Validators.required, Validators.minLength(1)]),

        'name': new FormControl('', Validators.required),
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
    );
  }

  ngOnInit() {
    this.institutionService.getInstitutionTypes().subscribe(
      value => this.allType = value,
      error => console.warn(error)
    )
  }

  private loadTypes = (): void => {

  };

  submit() {
    if (!this.isNormalUser && this.isPasswordValid) {
      this.registerForm.markAllAsTouched();
      const formData: ProviderAccountRegisterModel = this.registerForm.value;
      this.accountService.saveProviderAccount(formData).subscribe(
        () => {
          this.router.navigate(['registration-complete']);
        },
        error => {
          validationHandler(error, this.registerForm);
          (this.registerForm.get('institutions') as FormArray)
            .controls.forEach((control) => validationHandler(error, (control as FormGroup)))
        }
      );
    }
  }

  removeInstitution(index) {
    (this.registerForm.get('institutions') as FormArray).removeAt(index);
  }

  addNewInstitution() {
    (this.registerForm.get('institutions') as FormArray).push(new FormGroup({
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
    }));
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

  getInstitutions() {
    return this.registerForm.get('institutions') as FormArray;
  }

  setIfPasswordIsValid = (isValid: boolean) => {
    this.isPasswordValid = isValid;
  };

}
