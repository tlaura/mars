import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-provider-attributes',
  templateUrl: './provider-attributes.component.html',
  styleUrls: ['./provider-attributes.component.css']
})
export class ProviderAttributesComponent implements OnInit {
  NAME_REGEX = '^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.\'-]+$';

  //TODO: add new type by admin
  allType: string[] = ['diagnózis központ', 'terápia', 'fejlesztő hely', 'óvoda', 'általános iskola', 'középiskola', 'kollégium', 'munkahely', 'bentlakásos felnőtt ellátó', 'nappali foglalkoztató', 'egyéb'];

  @Input()
  providerAttributesFormGroup: FormGroup;

  passwordAgainFormGroup: FormGroup;

  password: FormControl;
  passwordMatch: boolean;

  constructor() {
    this.providerAttributesFormGroup = new FormGroup({});
    this.passwordAgainFormGroup = new FormGroup({
      password: new FormControl(''),
    });
    this.passwordMatch = true;
  }

  validatePassword = (formControl: FormControl) => {
    let isValid: boolean = formControl.value == this.providerAttributesFormGroup.get('password').value;
    debugger;
    return isValid ? null : {
      'validatePassword': true
    }
  };

  ngOnInit(): void {
    this.providerAttributesFormGroup.get('password').setValidators([this.validatePassword, Validators.required]);
    this.passwordAgainFormGroup.get('password').valueChanges.subscribe(
      () => this.updateValidator(),
      error => console.warn(error),
      () => console.log("validity rechecked")
    );
  }

  getAgesArray(n: number) {
    return Array(n);
  }

  updateValidator() {
    this.providerAttributesFormGroup.get('password').setValidators([this.validatePassword, Validators.required]);
    this.providerAttributesFormGroup.updateValueAndValidity();
    debugger;
  }
}
