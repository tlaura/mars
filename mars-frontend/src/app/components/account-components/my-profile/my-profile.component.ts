import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProviderUserProfileDetailsModel} from "../../../models/providerUserProfileDetails.model";
import {AccountService} from "../../../services/account.service";
import {FormControl, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {InstitutionTypeModel} from "../../../models/InstitutionType.model";
import {InstitutionService} from "../../../services/institution.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/auth/authentication.service";
import decode from 'jwt-decode';

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

  // editGroup = new FormGroup({
  username = new FormControl('', Validators.required);
  providerServiceName = new FormControl('', Validators.required);
  phone = new FormControl('', [Validators.required, Validators.pattern(validatorBounds.phoneRegex)]);
  // types = new FormControl(null, [Validators.required, Validators.minLength(1)]);
  // });

  allTypes: Array<InstitutionTypeModel> = [];

  constructor(private authenticationService: AuthenticationService,
              public providerService: AccountService,
              private institutionService: InstitutionService,
              private router: Router) {
  }

  editModeChange = () => {
    this.editMode = this.editMode === false;
  };

  ngOnInit(): void {
    if (!this.authenticationService.currentUserValue.token) {
      this.router.navigate(['login']);
    }
    const tokenPayload = decode(this.authenticationService.currentUserValue.token);
    this.loggedInUser = tokenPayload.sub;

    this.providerService.fetchProviderAccountDetails(this.loggedInUser).subscribe(
      (providerDetails: ProviderUserProfileDetailsModel) => {
        this.providerAccount = providerDetails;
        this.accountCopy = Object.assign({}, providerDetails);
      }, error => {
        console.warn(error)
      }, () => {
        this.getAllProviderTypes();
      }
    )
  }

  saveChanges = () => {
    // if(this.providerAccount.id == null) error
    this.providerService.editProviderAccountDetails(this.providerAccount, this.providerAccount.id).subscribe(
      () => {
        if (this.editMode) {
          this.editMode = false;
          // this.providerAccount.types = this.types;
          this.accountCopy = Object.assign({}, this.providerAccount);
        }
        console.log("Data changes saved");
      }, error => {
        console.log(error);
      }
    );
  };

  private getAllProviderTypes = () => {
    this.institutionService.getProviderTypes().subscribe(
      allTypes => this.allTypes = allTypes,
      error => console.warn(error)
    );
  };

  cancelEdit = () => {
    this.editMode = false;
    this.providerAccount = Object.assign({}, this.accountCopy);
  };

  deleteInstitution = (id: number) => {
    this.providerService.deleteInstitution(id, this.loggedInUser).subscribe(
      () => {
        let elementPos = this.providerAccount.institutionList
          .map(inst => inst.id)
          .indexOf(id);
        this.providerAccount.institutionList.splice(elementPos, 1);
        console.log("Institution deleted");
      }, error => {
        console.log(error);
      }
    );
  };

  changePassword() {
    this.router.navigate(['password-change']);
  }
}
