import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loginForm: FormGroup;

  constructor(private loginService: LoginService, private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
      }
    )
  }

  login = () => {
    const loginData = {...this.loginForm.value};
    console.log(loginData);

    this.loginService.authenticate(loginData).subscribe(
      response => {
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['']);
      },
      error => {
        console.warn(error)
      }
    )
  }
}
