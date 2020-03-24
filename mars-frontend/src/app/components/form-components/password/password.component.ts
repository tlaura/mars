import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {
  PASSWORD_REGEX = '(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$';

  @Input()
  passwordFormGroup: FormGroup = new FormGroup({});

  constructor() {
  }

  ngOnInit(): void {
  }

}
