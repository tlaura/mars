import {Component, HostListener, OnInit} from '@angular/core';
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
  isNormalUser: boolean = false;
  haveProviderCustomAddress: boolean = false;
  registerForm: FormGroup;

  constructor(private accountService: AccountService, private router: Router) {
    this.chooseProviderUser();
    this.registerForm = new FormGroup(
      {
        'providerServiceName': new FormControl('', Validators.required),
        'password': new FormControl('', Validators.required),
        'ageGroupMin': new FormControl(0),
        'ageGroupMax': new FormControl(99),
        'types': new FormControl(null),

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

  getInstitutions() {
    return this.registerForm.get('institutions') as FormArray;
  }

  @HostListener('abort', ['$event'])
  @HostListener('afterprint', ['$event'])
  @HostListener('animationend', ['$event'])
  @HostListener('animationiteration', ['$event'])
  @HostListener('animationstart', ['$event'])
  @HostListener('beforeprint', ['$event'])
  @HostListener('beforeunload', ['$event'])
  @HostListener('blur', ['$event'])
  @HostListener('canplay', ['$event'])
  @HostListener('canplaythrough', ['$event'])
  @HostListener('change', ['$event'])
  @HostListener('click', ['$event'])
  @HostListener('contextmenu', ['$event'])
  @HostListener('copy', ['$event'])
  @HostListener('cut', ['$event'])
  @HostListener('dblclick', ['$event'])
  @HostListener('drag', ['$event'])
  @HostListener('dragend', ['$event'])
  @HostListener('dragenter', ['$event'])
  @HostListener('dragleave', ['$event'])
  @HostListener('dragover', ['$event'])
  @HostListener('dragstart', ['$event'])
  @HostListener('drop', ['$event'])
  @HostListener('durationchange', ['$event'])
  @HostListener('ended', ['$event'])
  @HostListener('error', ['$event'])
  @HostListener('focus', ['$event'])
  @HostListener('focusin', ['$event'])
  @HostListener('focusout', ['$event'])
  @HostListener('fullscreenchange', ['$event'])
  @HostListener('fullscreenerror', ['$event'])
  @HostListener('hashchange', ['$event'])
  @HostListener('input', ['$event'])
  @HostListener('invalid', ['$event'])
  @HostListener('keydown', ['$event'])
  @HostListener('keypress', ['$event'])
  @HostListener('keyup', ['$event'])
  @HostListener('load', ['$event'])
  @HostListener('loadeddata', ['$event'])
  @HostListener('loadedmetadata', ['$event'])
  @HostListener('loadstart', ['$event'])
  @HostListener('message', ['$event'])
  @HostListener('mousedown', ['$event'])
  @HostListener('mouseenter', ['$event'])
  @HostListener('mouseleave', ['$event'])
  @HostListener('mousemove', ['$event'])
  @HostListener('mouseover', ['$event'])
  @HostListener('mouseout', ['$event'])
  @HostListener('mouseup', ['$event'])
  @HostListener('mousewheel', ['$event'])
  @HostListener('offline', ['$event'])
  @HostListener('online', ['$event'])
  @HostListener('open', ['$event'])
  @HostListener('pagehide', ['$event'])
  @HostListener('pageshow', ['$event'])
  @HostListener('paste', ['$event'])
  @HostListener('pause', ['$event'])
  @HostListener('play', ['$event'])
  @HostListener('playing', ['$event'])
  @HostListener('popstate', ['$event'])
  @HostListener('progress', ['$event'])
  @HostListener('ratechange', ['$event'])
  @HostListener('resize', ['$event'])
  @HostListener('reset', ['$event'])
  @HostListener('scroll', ['$event'])
  @HostListener('search', ['$event'])
  @HostListener('seeked', ['$event'])
  @HostListener('seeking', ['$event'])
  @HostListener('select', ['$event'])
  @HostListener('show', ['$event'])
  @HostListener('stalled', ['$event'])
  @HostListener('storage', ['$event'])
  @HostListener('submit', ['$event'])
  @HostListener('suspend', ['$event'])
  @HostListener('timeupdate', ['$event'])
  @HostListener('toggle', ['$event'])
  @HostListener('touchcancel', ['$event'])
  @HostListener('touchend', ['$event'])
  @HostListener('touchmove', ['$event'])
  @HostListener('touchstart', ['$event'])
  @HostListener('transitionend', ['$event'])
  @HostListener('unload', ['$event'])
  @HostListener('volumechange', ['$event'])
  @HostListener('waiting', ['$event'])
  @HostListener('wheel', ['$event'])
  public onAnything($event): void {
    console.log(`onAnything ${$event.type}`);
  }

}
