import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;
  showErrorMessage = false;

  constructor(private loginService: LoginService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'userName': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)
      }
    )
  }

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
        this.showErrorMessage = true;
      }
    )
  }
}
