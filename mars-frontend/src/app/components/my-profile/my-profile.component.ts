import { Component, OnInit } from '@angular/core';
import {ProviderUserProfileDetailsModel} from "../../models/providerUserProfileDetails.model";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  providerUser: ProviderUserProfileDetailsModel;
  loggedInUser;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.loggedInUser = this.loginService.getCurrentUser().username;
  }
}
