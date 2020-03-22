import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {AccountService} from "../../services/account.service";
import {ProviderAccountEditDataModel} from "../../models/providerAccountEditData.model";
import {Router} from "@angular/router";
import {validationHandler} from "../../utils/validationHandler";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  providerAccountForm: FormGroup;
  loggedInUser;
  editMode = false;
  @Input() name: string;
  @Output() focusOut: EventEmitter<any> = new EventEmitter<any>();

  constructor(private loginService: LoginService,
              private providerService: AccountService,
              private router: Router) {
    this.providerAccountForm = new FormGroup({
      name: new FormControl('', Validators.required),
      providerServiceName: new FormControl('', Validators.required),
      // password: new FormControl(''),
      phone: new FormControl('', Validators.required),
      zipcode: new FormControl(''),
      city: new FormControl(''),
      address: new FormControl(''),
    })
  }

  ngOnInit(): void {
    this.loggedInUser = this.loginService.getCurrentUser()['name'];
    this.getProviderDetails(this.loggedInUser);
  }

  onFocusOut() {
    this.focusOut.emit(this.name);
  }


  getProviderDetails = (id: string) => {
    this.providerService.fetchProviderAccountEditDetails(this.loggedInUser).subscribe(
      (providerDetails: ProviderAccountEditDataModel) => {
        this.providerAccountForm.patchValue({
          name: providerDetails.name,
          providerServiceName: providerDetails.providerServiceName,
          // password: providerDetails.password,
          phone: providerDetails.phone,
          zipcode: providerDetails.zipcode,
          city: providerDetails.city,
          address: providerDetails.address,
          newsletter: providerDetails.newsletter
        })
      }, error => {
        console.warn(error)
        //  TODO - write unauthorized validation
      }
    )
  };

  saveChanges() {
    // if(this.providerAccountForm.valid) {
    let account: ProviderAccountEditDataModel = {...this.providerAccountForm.value};
    this.providerService.editProviderAccount(account, this.loggedInUser).subscribe(
      () => {
        console.log('data changes saved');
        this.router.navigate(['my-profile']);
      },
      error => {
        validationHandler(error, this.providerAccountForm);
      }
    )
  }

  goBack() {
    this.router.navigate(['my-profile']);
  }
}
