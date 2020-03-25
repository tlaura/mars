import {Component, Input, OnInit} from '@angular/core';
import {FormGroup, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {
  PASSWORD_REGEX = validatorBounds.passwordRegex;

  @Input()
  passwordFormGroup: FormGroup = new FormGroup({});

  constructor() {
  }

  ngOnInit(): void {
    this.passwordFormGroup.get("password").setValidators([Validators.required, Validators.pattern(this.PASSWORD_REGEX)]);
  }

}
