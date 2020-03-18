import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {
  PASSWORD_REGEX = '(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,15})$';

  @Output()
  passwordEmitter: EventEmitter<string>;

  password: FormControl;


  constructor() {
    this.password = new FormControl('', Validators.pattern(this.PASSWORD_REGEX));
    this.passwordEmitter = new EventEmitter<string>();
  }

  ngOnInit(): void {
  }

  emitPassword() {
    this.passwordEmitter.emit(this.password.value);
  }
}
