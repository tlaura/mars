import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

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
  NAME_REGEX = '^[A-Z][a-z]{2,15}$';
  CURRENT_YEAR = new Date().getFullYear().valueOf();

  isNormalUser: boolean = true;
  registerForm: FormGroup;

  constructor() {
    this.registerForm = new FormGroup(
      {
        'lastname': new FormControl('', Validators.pattern(this.NAME_REGEX)),
        'firstname': new FormControl('', Validators.pattern(this.NAME_REGEX)),
        'username': new FormControl('',
          [Validators.required, Validators.pattern(this.USERNAME_REGEX)]),
        'password': new FormControl('',
          [Validators.required, Validators.pattern(this.PASSWORD_REGEX)]),
        'email': new FormControl('',
          [Validators.required, Validators.pattern(this.EMAIL_REGEX)]),
        'phone': new FormControl('', Validators.pattern(this.PHONE_REGEX)),
        'zipcode': new FormControl('',
          [Validators.required, Validators.pattern(this.ZIPCODE_REGEX)]),
        'city': new FormControl(''),
        'address': new FormControl(''),
        'birthyear': new FormControl('',
          [Validators.required, Validators.min(this.CURRENT_YEAR - 50), Validators.max(this.CURRENT_YEAR)]),
        'newsletter': new FormControl(''),
        'termsAndConditions': new FormControl('', Validators.requiredTrue)
      }
    )
  }

  ngOnInit() {
  }

  submit() {
  }


}
