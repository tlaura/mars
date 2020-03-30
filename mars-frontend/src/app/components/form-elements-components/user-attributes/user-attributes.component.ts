import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-user-attributes',
  templateUrl: './user-attributes.component.html',
  styleUrls: ['./user-attributes.component.css']
})
export class UserAttributesComponent implements OnInit {

  @Input()
  userAttributesFormGroup: FormGroup;

  @Output()
  isPasswordValid: EventEmitter<boolean> = new EventEmitter<boolean>();

  passwordAgainFormGroup: FormGroup;

  password: FormControl;
  passwordMatch: boolean;

  constructor() {
    this.userAttributesFormGroup = new FormGroup({});
    this.passwordAgainFormGroup = new FormGroup({
      password: new FormControl(''),
    });
    this.passwordMatch = true;
  }

  checkPassword = (): boolean => {
    let isPasswordValid: boolean = this.userAttributesFormGroup.get('password').value === this.passwordAgainFormGroup.get('password').value;
    this.isPasswordValid.emit(isPasswordValid);
    return isPasswordValid;
  };

  ngOnInit(): void {
    this.passwordAgainFormGroup.get('password').valueChanges.subscribe(
      () => this.checkPassword(),
      error => console.warn(error),
    );
    this.userAttributesFormGroup.get('password').valueChanges.subscribe(
      () => this.checkPassword(),
      error => console.warn(error),
    );
  }
}
