import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

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
        'username': new FormControl(''),
        'password': new FormControl(''),
        'email': new FormControl(''),
        'phone': new FormControl(''),
        'zipcode': new FormControl(''),
        'city': new FormControl(''),
        'address': new FormControl(''),
        'newsletter': new FormControl(''),
        'termsAndConditions': new FormControl('')
      }
    )
  }

  ngOnInit() {
  }

  submit() {

  }
}
