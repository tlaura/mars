import { Component, OnInit } from '@angular/core';
import {ProviderUserProfileDetailsModel} from "../../models/providerUserProfileDetails.model";
import {LoginService} from "../../services/login.service";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  providerAccount: ProviderUserProfileDetailsModel;
  loggedInUser;

  constructor(private loginService: LoginService, private providerService: AccountService) { }

  ngOnInit(): void {
    this.loggedInUser = this.loginService.getCurrentUser().username;
    this.providerService.fetchProviderAccountDetails(this.loggedInUser).subscribe(
      (providerDetails: ProviderUserProfileDetailsModel) => {
        this.providerAccount = providerDetails;
        console.log(this.providerAccount);
      }, error => {
        console.warn(error)
      //  TODO - write unauthorized validation
      }
    )
  }
}
