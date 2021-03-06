import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {InstitutionTypeModel} from "../../../account-institution/institution/models/InstitutionType.model";

@Component({
  selector: 'app-provider-attributes',
  templateUrl: './provider-attributes.component.html',
  styleUrls: ['./provider-attributes.component.css']
})
export class ProviderAttributesComponent implements OnInit {

  //TODO: add new type by admin
  @Input()
  allType: Array<InstitutionTypeModel> = [];

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
    this.providerAttributesFormGroup.get('providerServiceName').setValidators([Validators.required]);
  }

  getAgesArray(n: number) {
    return Array(n);
  }
}
