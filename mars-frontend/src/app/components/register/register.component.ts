import {Component, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ProviderAccountRegisterModel} from "../../models/providerAccountRegisterModel";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";
import {Institution} from "../../models/institution";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  EMAIL_REGEX = '(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])';
  USERNAME_REGEX = '[a-zA-Z0-9]{5,15}$';
  PASSWORD_REGEX = '(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$';
  ZIPCODE_REGEX = '^[1-9][0-9]{3}$';
  PHONE_REGEX = '^\\+(\\d{1,2})\\D*(\\d{1,3})\\D*(\\d{3})\\D*(\\d{3,4})$';
  NAME_REGEX = '^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.\'-]+$';
  CURRENT_YEAR = new Date().getFullYear().valueOf();
  isNormalUser: boolean = true;
  haveProviderCustomAddress: boolean = false;
  types: string[] = ['diagnózis központ', ' terápia', 'fejlesztő hely', 'óvoda', 'általános iskola', 'középiskola', 'kollégium', 'munkahely', 'bentlakásos felnőtt ellátó', 'nappali foglalkoztató', 'egyéb'];
  weekDays: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];
  registerForm: FormGroup;
  openingHours = new FormArray([], Validators.minLength(1));
  institutions = new FormArray([]);
  allInstitution: Institution[] = [{
    name: 'Új intézmény hozzáadása',
    zipcode: 0,
    city: '',
    address: '',
    description: ''
  },
    {
      name: 'kórház1',
      zipcode: 1180,
      city: 'Budapest',
      address: 'Csontváry K. T. utca 13.',
      description: 'Itt lakom látod, ez az a ház...'
    }];

  // TODO: refactor -> new component?

  constructor(private accountService: AccountService, private router: Router) {
    this.isNormalUser = true;
    this.registerForm = this.getCommonFieldsFormGroup();
    this.addFormGroup(this.getNormalAccountRegisterFormGroup());
    this.addNewOpeningHours();
  }

  ngOnInit() {
  }

  submit() {
    this.registerForm.markAllAsTouched();
    const formData: ProviderAccountRegisterModel = this.registerForm.value;
    this.accountService.saveProviderAccount(formData).subscribe(
      () => {
        // TODO: register email...
        this.router.navigate(['registration-complete']);
      },
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

  getCommonFieldsFormGroup(): FormGroup {
    return new FormGroup(
      {
        'name': new FormControl('',
          [Validators.required, Validators.pattern(this.NAME_REGEX)]),
        'username': new FormControl('',
          [Validators.required, Validators.pattern(this.USERNAME_REGEX)]),
        'password': new FormControl('',
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
        'termsAndConditions': new FormControl('', Validators.requiredTrue)
      }
    )
  }

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
        'type': new FormControl(this.types[0]),
        'openingHours': this.openingHours,
        'ageGroupMin': new FormControl(0),
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

  removeOpeningHours() {
    this.openingHours.controls.pop();
    this.registerForm.setControl('openingHours', this.openingHours);
  }

  addNewInstitution() {
    const group = new FormGroup({
      institution: new FormControl(this.allInstitution[0].name),
      name: new FormControl('', Validators.required),
      zipcode: new FormControl('',
        [Validators.pattern(this.ZIPCODE_REGEX), Validators.required]),
      city: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });

    this.institutions.push(group);
  }

  removeInstitution() {
    this.institutions.controls.pop();
    this.registerForm.setControl('institutions', this.institutions);
  }

  updateValues(group: FormGroup) {
    const currentInstitutionName = group.get('institution').value;
    const currentInstitution = this.allInstitution.find(institution => institution.name == currentInstitutionName);
    if (this.allInstitution[0].name != currentInstitution.name) {
      group.setValue({
        institution: currentInstitution.name,
        name: currentInstitution.name,
        zipcode: currentInstitution.zipcode,
        city: currentInstitution.city,
        address: currentInstitution.address,
        description: currentInstitution.description
      });
    }
  }
}
