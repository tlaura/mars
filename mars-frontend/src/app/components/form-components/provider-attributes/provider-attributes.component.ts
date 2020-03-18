import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Form, FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-provider-attributes',
  templateUrl: './provider-attributes.component.html',
  styleUrls: ['./provider-attributes.component.css']
})
export class ProviderAttributesComponent implements OnInit {
  NAME_REGEX = '^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.\'-]+$';

  //TODO: add new type by admin
  allType: string[] = ['diagnózis központ', 'terápia', 'fejlesztő hely', 'óvoda', 'általános iskola', 'középiskola', 'kollégium', 'munkahely', 'bentlakásos felnőtt ellátó', 'nappali foglalkoztató', 'egyéb'];

  @Output()
  passwordEmitter: EventEmitter<string>;
  @Output()
  providerServiceNameEmitter: EventEmitter<string>;
  @Output()
  ageGroupMinEmitter: EventEmitter<string>;
  @Output()
  ageGroupMaxEmitter: EventEmitter<string>;
  @Output()
  typesEmitter: EventEmitter<string[]>;

  password: FormControl;
  passwordAgain: FormControl;
  passwordMatch: boolean;

  providerServiceName: FormControl;
  ageGroupMin: FormControl;
  ageGroupMax: FormControl;
  types: FormControl;

  constructor() {
    this.password = new FormControl('');
    this.providerServiceName = new FormControl('', Validators.pattern(this.NAME_REGEX));
    this.ageGroupMax = new FormControl(99, [Validators.min(0), Validators.max(99)]);
    this.ageGroupMin = new FormControl(0, [Validators.min(0), Validators.max(99)]);
    this.types = new FormControl(null, Validators.required);
    this.passwordAgain = new FormControl('');
    this.passwordEmitter = new EventEmitter<string>();
    this.providerServiceNameEmitter = new EventEmitter<string>();
    this.ageGroupMinEmitter = new EventEmitter<string>();
    this.ageGroupMaxEmitter = new EventEmitter<string>();
    this.typesEmitter = new EventEmitter<string[]>();
    this.passwordMatch = true;
  }

  ngOnInit(): void {
  }

  checkPasswords() {
    this.passwordMatch = this.password.value == this.passwordAgain.value;
    if (this.passwordMatch) {
      this.passwordEmitter.emit(this.password.value);
    }
  }

  setPassword(password: string) {
    this.password.setValue(password);
    this.checkPasswords();
  }

  setPasswordAgain(passwordAgain: string) {
    this.passwordAgain.setValue(passwordAgain);
    this.checkPasswords();
  }

  emitProviderServiceName() {
    this.providerServiceNameEmitter.emit(this.providerServiceName.value);
  }

  emitAgeGroupMin() {
    this.ageGroupMinEmitter.emit(this.ageGroupMin.value);
  }

  emitAgeGroupMax() {
    this.ageGroupMaxEmitter.emit(this.ageGroupMax.value);
  }

  emitTypes() {
    this.typesEmitter.emit(this.types.value);
  }


  getAgesArray(n: number) {
    return Array(n);
  }
}
