import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {InstitutionTypeModel} from "../../../../../account-institution/institution/models/InstitutionType.model";
import {AccountService} from "../../../../../account-institution/account/services/account.service";
import {Router} from "@angular/router";
import {InstitutionService} from "../../../../../account-institution/institution/services/institution.service";
import {validatorBounds} from "../../../../../../environments/validatorBounds";
import {ProviderAccountRegisterModel} from "../../../../../account-institution/account/models/providerAccountRegisterModel";
import {validationHandler} from "../../../../../utils/validators/validationHandler";
import {InstitutionListModel} from "../../../../../account-institution/institution/models/institutionList.model";

@Component({
  selector: 'app-provider-user',
  templateUrl: './provider-user.component.html',
  styleUrls: ['./provider-user.component.css']
})
export class ProviderUserComponent implements OnInit {

  haveProviderCustomAddress: boolean = false;
  isPasswordValid: boolean = false;
  registerForm: FormGroup;
  institutionList: Array<InstitutionListModel>;
  institutionIndex: number = -1;
  institutionSelected: boolean = false;
  institutionModel: InstitutionListModel;

  allType: Array<InstitutionTypeModel> = [];

  constructor(private accountService: AccountService, private router: Router, private institutionService: InstitutionService) {
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
    this.getProviderTypes();
    this.getAllInstitutions();
  }

  getProviderTypes = (): void => {
    this.institutionService.getProviderTypes().subscribe(
      value => this.allType = value,
      error => console.warn(error)
    );
  };

  getAllInstitutions = (): void => {
    this.institutionService.getAllInstitutions().subscribe(
      value => this.institutionList = value,
      error => console.warn(error)
    );
  };

  submit() {
    if (this.isPasswordValid) {
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


  fillFields = (event): void => {
    let index: number = event.target.value;
    if (index !== -1) {
      this.institutionSelected = true;
      this.institutionModel = this.institutionList[index];
    } else {
      this.institutionSelected = false;
    }

  };
}
