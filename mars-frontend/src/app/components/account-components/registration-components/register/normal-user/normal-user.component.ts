import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../../../services/account.service";
import {Router} from "@angular/router";
import {validationHandler} from "../../../../../utils/validationHandler";
import {NormalAccountRegisterModel} from "../../../../../models/normalAccountRegisterModel";

@Component({
  selector: 'app-normal-user',
  templateUrl: './normal-user.component.html',
  styleUrls: ['./normal-user.component.css']
})
export class NormalUserComponent implements OnInit {
  haveUserCustomAddress: boolean = false;
  isPasswordValid: boolean = false;
  registerForm: FormGroup;
  addressFormGroup: FormGroup;

  constructor(private accountService: AccountService, private router: Router) {
  }

  ngOnInit(): void {
    this.registerForm = new FormGroup(
      {
        'password': new FormControl(''),

        'name': new FormControl('', Validators.required),
        'email': new FormControl('', Validators.required),
        'phone': new FormControl(''),
        'website': new FormControl(''),

        'zipcode': new FormControl(null),
        'city': new FormControl(null),
        'address': new FormControl(null),

        'newsletter': new FormControl(false),
        'termsAndConditions': new FormControl(false, Validators.requiredTrue)
      })
  }

  changeUserAddress() {
    this.haveUserCustomAddress = !this.haveUserCustomAddress;
    if (!this.haveUserCustomAddress) {
      this.registerForm.get('zipcode').reset();
      this.registerForm.get('city').reset();
      this.registerForm.get('address').reset();
    }
  }

  setIfPasswordIsValid = (isValid: boolean) => {
    this.isPasswordValid = isValid;
  };

  submit() {
    if (this.isPasswordValid) {
      this.registerForm.markAllAsTouched();
      const formData: NormalAccountRegisterModel = this.registerForm.value;
      this.accountService.saveNormalAccount(formData).subscribe(
        () => {
          this.router.navigate(['registration-complete']);
        },
        error => {
          validationHandler(error, this.registerForm);
        }
      );
    }
  }
}
