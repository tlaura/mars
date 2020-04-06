import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../account-institution/account/services/account.service";
import {ProviderUserDetailsModel} from "../../account-institution/account/models/providerUserDetails.model";
import {ContactsService} from "../../chat/services/contacts.service";
import {AuthenticationService} from "../../auth/services/authentication.service";

@Component({
  selector: 'app-provider-details',
  templateUrl: './provider-details.component.html',
  styleUrls: ['./provider-details.component.css']
})
export class ProviderDetailsComponent implements OnInit {

  @Input() mailSender: boolean = false;
  @Input() isInInfoBox: boolean = false;
  @Input() providerUserDetail: ProviderUserDetailsModel;
  isLoggedIn: boolean;

  constructor(private activatedRoute: ActivatedRoute, private accountService: AccountService,
              private router: Router, private contactsService: ContactsService, private authenticationService: AuthenticationService) {

  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      paramMap => {
        const id: number = Number(paramMap.get('id'));
        if (id) {
          this.getProviderDetails(id);
        }
      }
    );
    this.isLoggedIn = this.authenticationService.isAuthenticated();
  }

  openMailSend = (): void => {
    this.mailSender = this.mailSender === false;
  };

  backToInstitutionList() {
    this.router.navigate(['institution-list'])
  }

  private getProviderDetails = (id: number): void => {
    this.accountService.getProviderAccountDetails(id).subscribe(
      value => this.providerUserDetail = value,
      error => console.warn(error),
    )
  };


  addContact(email: string) {
    this.contactsService.addContact(email).subscribe(
      () => console.log('ContactModel added.'),
      (error) => console.log(error),
      () => this.contactsService.contactSubject.next('New contact added.')
    )
  }
}
