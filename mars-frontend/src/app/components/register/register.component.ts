import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  isNormalUser: boolean = true;
  registerForm: FormGroup;

  constructor() {
    this.registerForm = new FormGroup(
      {
        'lastname': new FormControl(''),
        'firstname': new FormControl(''),
        'username': new FormControl('', Validators.required),
        'password': new FormControl('', Validators.required),
        'email': new FormControl('', Validators.required),
        'phone': new FormControl(''),
        'zipcode': new FormControl('', Validators.required),
        'city': new FormControl(''),
        'address': new FormControl(''),
        'year': new FormControl('', Validators.required),
        'newsletter': new FormControl(''),
        'termsAndConditions': new FormControl('', Validators.required)
      }
    )
  }

  ngOnInit() {
  }

  submit() {

  }
}
