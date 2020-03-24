import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

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

  @Output()
  isPasswordValid: EventEmitter<boolean> = new EventEmitter<boolean>();

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

  checkPassword = (): boolean => {
    let isPasswordValid: boolean = this.providerAttributesFormGroup.get('password').value === this.passwordAgainFormGroup.get('password').value;
    this.isPasswordValid.emit(isPasswordValid);
    return isPasswordValid;
  };

  ngOnInit(): void {
    this.passwordAgainFormGroup.get('password').valueChanges.subscribe(
      () => this.checkPassword(),
      error => console.warn(error),
    );
    this.providerAttributesFormGroup.get('password').valueChanges.subscribe(
      () => this.checkPassword(),
      error => console.warn(error),
    );
  }

  getAgesArray(n: number) {
    return Array(n);
  }

}
