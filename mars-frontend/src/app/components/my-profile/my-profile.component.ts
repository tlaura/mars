import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  accountCopy: ProviderUserProfileDetailsModel;
  loggedInUser;
  editMode = false;
  @Input() name: string;
  @Output() focusOut: EventEmitter<any> = new EventEmitter<any>();

  constructor(private loginService: LoginService, public providerService: AccountService) {
  }

  editModeChange = () => {
    this.editMode = this.editMode === false;
  };

  ngOnInit(): void {
    this.loggedInUser = this.loginService.getCurrentUser()['name'];
    this.providerService.fetchProviderAccountDetails(this.loggedInUser).subscribe(
      (providerDetails: ProviderUserProfileDetailsModel) => {
        this.providerAccount = providerDetails;
        this.accountCopy = Object.assign({}, providerDetails);
      }, error => {
        console.warn(error)
      //  TODO - write unauthorized validation
      }
    )
  }


  saveChanges = () => {
    // if(this.providerAccount.id == null) error
    this.providerService.editProviderAccountDetails(this.providerAccount, this.providerAccount.id).subscribe(
      () => {
        this.editModeChange();
        console.log("Data changes saved");
      }, error => {
        console.log(error);
      }
    );
  };

  cancelEdit = () => {
    this.editMode = false;
    this.providerAccount = Object.assign({}, this.accountCopy);
  }
}
