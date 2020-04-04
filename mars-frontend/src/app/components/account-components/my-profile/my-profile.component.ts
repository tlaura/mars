import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProviderUserProfileDetailsModel} from "../../../account-institution/account/models/providerUserProfileDetails.model";
import {AccountService} from "../../../account-institution/account/services/account.service";
import {FormControl, Validators} from "@angular/forms";
import {validatorBounds} from "../../../../environments/validatorBounds";
import {InstitutionTypeModel} from "../../../account-institution/institution/models/InstitutionType.model";
import {InstitutionService} from "../../../account-institution/institution/services/institution.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthenticationService} from "../../../auth/services/authentication.service";
import decode from 'jwt-decode';
import {UserDetailsModel} from "../../../account-institution/account/models/userDetails.model";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {
  providerAccount: ProviderUserProfileDetailsModel;
  accountCopy: ProviderUserProfileDetailsModel;
  indUser: UserDetailsModel;
  indUserCopy: UserDetailsModel;
  isProvider: boolean;
  loggedInUser;
  editMode = false;
  @Input() name: string;
  @Output() focusOut: EventEmitter<any> = new EventEmitter<any>();

  username = new FormControl('', Validators.required);
  providerServiceName = new FormControl('', Validators.required);
  phone = new FormControl('', [Validators.required, Validators.pattern(validatorBounds.phoneRegex)]);

  allTypes: Array<InstitutionTypeModel> = [];

  constructor(private authenticationService: AuthenticationService,
              public providerService: AccountService,
              private institutionService: InstitutionService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  editModeChange = () => {
    this.editMode = this.editMode === false;
  };

  ngOnInit(): void {
    if (!this.authenticationService.currentTokenValue.token) {
      this.router.navigate(['login']);
    }
    const tokenPayload = decode(this.authenticationService.currentTokenValue.token);
    this.loggedInUser = tokenPayload.sub;

    this.activatedRoute.paramMap.subscribe(
      () => {
        const role = this.authenticationService.getRole();
        if (role === 'ROLE_IND') {
          this.isProvider = false;
          this.providerService.getUserDetails(this.loggedInUser).subscribe(
            userDetails => {
              this.indUser = userDetails;
              this.indUserCopy = Object.assign({}, userDetails);
            },
            error => console.warn(error),
          );
        } else {
          this.isProvider = true;
          this.providerService.fetchProviderAccountDetails(this.loggedInUser).subscribe(
            (providerDetails: ProviderUserProfileDetailsModel) => {
              this.providerAccount = providerDetails;
              this.accountCopy = Object.assign({}, providerDetails);
            }, error => {
              console.warn(error)
            }, () => {
              this.getAllProviderTypes();
            }
          );
        }
      }
    );


  }

  saveChanges = () => {
    // if(this.providerAccount.id == null) error
    if (this.isProvider) {
      this.providerService.editProviderAccountDetails(this.providerAccount, this.providerAccount.id).subscribe(
        () => {
          if (this.editMode) {
            this.editMode = false;
            this.accountCopy = Object.assign({}, this.providerAccount);
          }
        }, error => {
          console.log(error);
        }
      );
    } else {
      this.providerService.updateUserDetails(this.indUser).subscribe(
        value => {
          if (this.editMode) {
            this.editMode = false;
            this.indUser = value;
            this.indUserCopy = value;
          }
        },
        error => console.warn(error)
      );
    }

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
    this.indUser = Object.assign({}, this.indUserCopy);
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

  confirmDeletion() {
    this.router.navigate(['my-profile/confirm-deletion']);
  }
}
