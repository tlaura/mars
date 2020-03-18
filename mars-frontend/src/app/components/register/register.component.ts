import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
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
  CURRENT_YEAR = new Date().getFullYear().valueOf();
  isNormalUser: boolean = true;
  haveProviderCustomAddress: boolean = false;
  weekDays: string[] = ['Hétfő', 'Kedd', 'Szerda', 'Csütörtök', 'Péntek', 'Szombat', 'Vasárnap'];
  registerForm: FormGroup;
  institutions = new FormArray([]);
  allInstitution: Institution[] = [{
    id: null,
    name: 'Új intézmény hozzáadása',
    zipcode: 0,
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
    this.isNormalUser = false;
  }

  addFormGroup(formGroupToAdd: FormGroup) {
    for (let controlsKey in formGroupToAdd.controls) {
      this.registerForm.addControl(controlsKey, formGroupToAdd.get(controlsKey));
    }
  }


  currentPasswordAgain: string = '';

  getNormalAccountRegisterFormGroup(): FormGroup {
    return new FormGroup(
      {
        'birthyear': new FormControl('',
          [Validators.required, Validators.min(this.CURRENT_YEAR - 50), Validators.max(this.CURRENT_YEAR)]),
      })
  }


  addNewOpeningHours(control: AbstractControl) {

    const group = new FormGroup({
      weekDay: new FormControl(this.weekDays[0]),
      openingTime: new FormControl('', Validators.required),
      closingTime: new FormControl('', Validators.required),
    });

    (control as FormArray).push(group);
  }

  // TODO - opening hours
  removeOpeningHours(control: AbstractControl, targetIndex: number) {
    (control as FormArray).removeAt(targetIndex);
  }

  getCommonFieldsFormGroup(): FormGroup {
    return new FormGroup(
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

        'newsletter': new FormControl(false),
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
      zipcode: new FormControl(null,
        [, Validators.required]),
      city: new FormControl('', Validators.required),
      address: new FormControl('', Validators.required),
      description: new FormControl('',
        [Validators.required, Validators.minLength(30), Validators.maxLength(200)]),
      openingHours: new FormArray([])
    });
    this.addNewOpeningHours(group.get('openingHours'));
    this.institutions.push(group);
  }

  updateValues = (group: FormGroup) => {
    const currentInstitutionId = group.get('id').value;
    if (currentInstitutionId) {
      const currentInstitution = this.allInstitution.find(item => item.id == currentInstitutionId);
      group.setValue({
        id: currentInstitution.id,
        name: currentInstitution.name,
        zipcode: currentInstitution.zipcode,
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

  addCustomProviderAddress() {
    this.haveProviderCustomAddress = !this.haveProviderCustomAddress;
    if (!this.haveProviderCustomAddress) {
      this.registerForm.get('zipcode').reset();
      this.registerForm.get('city').reset();
      this.registerForm.get('address').reset();
    }
  }
}
