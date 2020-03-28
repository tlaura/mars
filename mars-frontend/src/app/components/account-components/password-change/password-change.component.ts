import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {matchingPasswords} from "../../../utils/password.validator";
import {Router} from "@angular/router";
import {LoginService} from "../../../services/login.service";
import {AccountService} from "../../../services/account.service";
import {PasswordChangeDetailsModel} from "../../../models/passwordChangeDetails.model";

@Component({
  selector: 'app-password-change',
  templateUrl: './password-change.component.html',
  styleUrls: ['./password-change.component.css']
})
export class PasswordChangeComponent implements OnInit {
  passwordChangeForm: FormGroup;
  loggedInUser: string;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private loginService: LoginService,
              private accountService: AccountService) {
  }

  ngOnInit(): void {
    this.loggedInUser = this.loginService.getCurrentUser()['name'];
    console.log(this.loggedInUser);
    this.passwordChangeForm = this.formBuilder.group({
      oldPassword: ['', [Validators.required, Validators.pattern(validatorBounds.passwordRegex)]],
      password: ['', [Validators.required, Validators.pattern(validatorBounds.passwordRegex)]],
      confirmPassword: ['', [Validators.required, Validators.pattern(validatorBounds.passwordRegex)]]
    }, {validator: matchingPasswords('password', 'confirmPassword')});
  }

  changePassword() {
    if (this.passwordChangeForm.valid) {
      const passwordChange: PasswordChangeDetailsModel = this.passwordChangeForm.value;
      passwordChange.email = this.loggedInUser;

      this.accountService.updatePassword(passwordChange).subscribe(
        () => {
          this.router.navigate(['my-profile']);
        }
      )
    }
  }

  // goBack() {
  //   this.router.navigate(['my-profile']);
  // }
}
