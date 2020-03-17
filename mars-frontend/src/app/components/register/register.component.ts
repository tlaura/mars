import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Institution} from "../../models/institution";
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
  EMAIL_REGEX = '(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])';
  PASSWORD_REGEX = '(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$';
  ZIPCODE_REGEX = '^[1-9][0-9]{3}$';
  PHONE_REGEX = '^\\+(\\d{1,2})\\D*(\\d{1,3})\\D*(\\d{3})\\D*(\\d{3,4})$';
  NAME_REGEX = '^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.\'-]+$';
  CURRENT_YEAR = new Date().getFullYear().valueOf();
  isNormalUser: boolean = true;
  haveProviderCustomAddress: boolean = false;
  // TODO:  fetch types from server?
  types: string[] = ['diagnózis központ', ' terápia', 'fejlesztő hely', 'óvoda', 'általános iskola', 'középiskola', 'kollégium', 'munkahely', 'bentlakásos felnőtt ellátó', 'nappali foglalkoztató', 'egyéb'];
  weekDays: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];
  registerForm: FormGroup;
  openingHours = new FormArray([]);
  institutions = new FormArray([]);
  allInstitution: Institution[] = [{
    id: null,
    name: 'Új intézmény hozzáadása',
    zipCode: 0,
    city: '',
    address: '',
    description: ''
  },
  ];

  // TODO: refactor ?

  constructor(private accountService: AccountService, private router: Router) {
    this.isNormalUser = true;
    this.registerForm = this.getCommonFieldsFormGroup();
    this.addFormGroup(this.getNormalAccountRegisterFormGroup());
    this.addNewOpeningHours();
  }

  ngOnInit() {
    this.accountService.fetchInstitutions().subscribe(
      institutions => this.allInstitution = this.allInstitution.concat(institutions)
    )
  }

  submit() {
    this.registerForm.markAllAsTouched();
    const formData: ProviderAccountRegisterModel = this.registerForm.value;
    this.accountService.saveProviderAccount(formData).subscribe(
      () => {
        // TODO: register email...
        this.router.navigate(['registration-complete']);
      },
      error => validationHandler(error, this.registerForm)
    );
  }

  renderNormalAccountRegisterForm() {
    this.registerForm = this.getCommonFieldsFormGroup();
    this.addFormGroup(this.getNormalAccountRegisterFormGroup());
    this.isNormalUser = true;
  }

  renderProviderAccountRegisterForm() {
    this.registerForm = this.getCommonFieldsFormGroup();
    this.addFormGroup(this.getProviderAccountRegisterFormGroup());
    this.isNormalUser = false;
  }

  addFormGroup(formGroupToAdd: FormGroup) {
    for (let controlsKey in formGroupToAdd.controls) {
      this.registerForm.addControl(controlsKey, formGroupToAdd.get(controlsKey));
    }
  }

  getAgesArray(n: number) {
    return Array(n);
  }

  currentPasswordAgain: string = '';

  getNormalAccountRegisterFormGroup(): FormGroup {
    return new FormGroup(
      {
        'birthyear': new FormControl('',
          [Validators.required, Validators.min(this.CURRENT_YEAR - 50), Validators.max(this.CURRENT_YEAR)]),
      })
  }

  getProviderAccountRegisterFormGroup(): FormGroup {
    return new FormGroup(
      {
        'type': new FormControl(null),
        'ageGroupMin': new FormControl(1),
        'ageGroupMax': new FormControl(99),
        'institutions': this.institutions,
      })
  }

  addNewOpeningHours() {
    const group = new FormGroup({
      weekDay: new FormControl(this.weekDays[0]),
      openingTime: new FormControl('', Validators.required),
      closingTime: new FormControl('', Validators.required),
    });

    this.openingHours.push(group);
  }

  // TODO - opening hours
  removeOpeningHours(group: FormGroup) {
    this.openingHours.controls.pop();
    group.setControl('openingHours', this.openingHours);
  }

  getCommonFieldsFormGroup(): FormGroup {
    return new FormGroup(
      {
        'providerServiceName': new FormControl('',
          [Validators.required, Validators.pattern(this.NAME_REGEX)]),
        'name': new FormControl('',
          [Validators.required, Validators.pattern(this.NAME_REGEX)]),
        'password': new FormControl('',
          [Validators.required, Validators.pattern(this.PASSWORD_REGEX)]),
        'passwordAgain': new FormControl('',
          [Validators.required, Validators.pattern(this.PASSWORD_REGEX)]),
        'email': new FormControl('',
          [Validators.required, Validators.pattern(this.EMAIL_REGEX)]),
        'phone': new FormControl('',
          [Validators.required, Validators.pattern(this.PHONE_REGEX)]),

        'zipcode': new FormControl('',
          [Validators.pattern(this.ZIPCODE_REGEX)]),
        'city': new FormControl(''),
        'address': new FormControl(''),

        'newsletter': new FormControl(''),
        'termsAndConditions': new FormControl(false, Validators.requiredTrue)
      }
    )
  }

  removeInstitution() {
    this.institutions.controls.pop();
    this.registerForm.setControl('institutions', this.institutions);
  }

  addNewInstitution() {
    const group = new FormGroup({
      id: new FormControl(null),
      name: new FormControl('',
        [Validators.required]),
      zipCode: new FormControl('',
        [Validators.pattern(this.ZIPCODE_REGEX), Validators.required]),
      city: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      description: new FormControl('',
        [Validators.required, Validators.minLength(30), Validators.maxLength(200)]),
      openingHours: this.openingHours
    });

    this.institutions.push(group);
  }

  updateValues = (group: FormGroup) => {
    const currentInstitutionId = group.get('id').value;
    if (currentInstitutionId) {
      const currentInstitution = this.allInstitution.find(item => item.id == currentInstitutionId);
      group.setValue({
        id: currentInstitution.id,
        name: currentInstitution.name,
        zipCode: currentInstitution.zipCode,
        city: currentInstitution.city,
        address: currentInstitution.address,
        description: currentInstitution.description
      });
      for (let controlsKey in group.controls) {
        if (controlsKey != 'id') {
          group.get(controlsKey).disable();
        }
      }
    } else {
      group.reset();
      group.enable();
    }
  };

  checkPasswords = () => {
    this.currentPasswordAgain = this.registerForm.get('passwordAgain').value;
    this.registerForm.get('password').setValidators([Validators.required, Validators.pattern(this.PASSWORD_REGEX), this.passwordMatchValidator]);
    this.registerForm.get('password').updateValueAndValidity();
  };

  passwordMatchValidator = (control: FormControl) => {
    if (control.value != this.currentPasswordAgain) {
      return {passwordMismatch: true};
    }
    return null;
  }
}
