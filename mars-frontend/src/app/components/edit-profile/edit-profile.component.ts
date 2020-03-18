import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {LoginService} from "../../services/login.service";
import {AccountService} from "../../services/account.service";
import {ProviderUserProfileEditDetailsModel} from "../../models/providerUserProfileEditDetails.model";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  providerAccountForm: FormGroup;
  loggedInUser;

  constructor(private loginService: LoginService,
              private providerService: AccountService) {
  }

  ngOnInit(): void {
    this.loggedInUser = this.loginService.getCurrentUser()['name'];
    this.getProviderDetails(this.loggedInUser);
  }

  getProviderDetails = (id: string) => {
    this.providerService.fetchProviderAccountEditDetails(this.loggedInUser).subscribe(
      (providerDetails: ProviderUserProfileEditDetailsModel) => {
        this.providerAccountForm.patchValue({
          name: providerDetails.name,
          providerServiceName: providerDetails.providerServiceName,
          password: providerDetails.password,
          phone: providerDetails.phone,
          zipcode: providerDetails.zipcode,
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

  }
}
