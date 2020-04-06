import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {AuthenticationService} from "../../../auth/services/authentication.service";
import {first} from "rxjs/operators";


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;
  showUnauthorizedMessage = false;
  showUnconfirmedMessage = false;
  returnUrl: string;
  loading = false;
  submitted = false;
  error = null;
  isPasswordVisible: boolean = false;

  constructor(private authenticationService: AuthenticationService, private route: ActivatedRoute, private formBuilder: FormBuilder, private router: Router) {
    // redirect to home if already logged in
    if (this.authenticationService.currentTokenValue) {
      this.router.navigate(['/']);
    }
  }

  // convenience getter for easy access to form fields
  get controls() {
    return this.loginForm.controls;
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'email': new FormControl('', [Validators.required, Validators.pattern(validatorBounds.emailRegex)]),
      'password': new FormControl('', Validators.required)
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '';
  }

  login = () => {
    const loginData = {...this.loginForm.value};

    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    this.authenticationService.login(this.controls.email.value, this.controls.password.value)
      .pipe(first())
      .subscribe(
        data => {
          this.router.navigate([this.returnUrl]);
        },
        error => {
          if (error != 'OK') {
            this.error = "Kérjük, aktiváld a fiókod az email-ben kapott üzenet alapján!";
          } else {
            this.error = "Helytelen felhasználónév vagy jelszó!";
          }
          this.loading = false;
        });

  };

  changePasswordVisibility() {
    this.isPasswordVisible = !this.isPasswordVisible;
    this.isPasswordVisible ?
      document.getElementById('password').setAttribute('type', 'text')
      :
      document.getElementById('password').setAttribute('type', 'password')
  }

}
