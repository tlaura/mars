import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {validatorBounds} from "../../../environments/validatorBounds";


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;
  showUnauthorizedMessage = false;
  showUnconfirmedMessage = false;

  constructor(private loginService: LoginService, private formBuilder: FormBuilder, private router: Router) {
  }

  ngOnInit() {
    this.loginService.loggedIn$.subscribe((userLoggedIn) => {
      if (userLoggedIn) {
        this.router.navigate(['']);
      }
    });
    this.loginForm = new FormGroup({
        'userName': new FormControl('', [Validators.required, Validators.pattern(validatorBounds.emailRegex)]),
        'password': new FormControl('', Validators.required)
      }
    )
  }

  isPasswordVisible: boolean = false;

  login = () => {
    const loginData = {...this.loginForm.value};

    this.loginService.login(loginData).subscribe(
      response => {
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['']);
        this.loginService.loggedIn$.next(true);
      },
      error => {
        console.warn(error);
        if ((error as HttpErrorResponse).status === 403) {
          this.showUnconfirmedMessage = true;
        } else {
          this.showUnauthorizedMessage = true;
        }
      }
    )
  };

  changePasswordVisibility() {
    this.isPasswordVisible = !this.isPasswordVisible;
    this.isPasswordVisible ?
      document.getElementById('password').setAttribute('type', 'text')
      :
      document.getElementById('password').setAttribute('type', 'password')
  }

  forgotPassword() {
    this.router.navigate(['new-password']);
  }
}
