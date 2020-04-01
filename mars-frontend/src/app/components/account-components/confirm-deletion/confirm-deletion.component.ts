import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/auth/authentication.service";
import decode from 'jwt-decode';
import {AccountService} from "../../../services/account.service";

@Component({
  selector: 'app-confirm-deletion',
  templateUrl: './confirm-deletion.component.html',
  styleUrls: ['./confirm-deletion.component.css']
})
export class ConfirmDeletionComponent implements OnInit {
  loggedInUser: string;

  constructor(private router: Router, private authenticationService: AuthenticationService, private accountService: AccountService) {
  }

  ngOnInit(): void {
    if (!this.authenticationService.currentTokenValue.token) {
      this.router.navigate(['login']);
    }
    const tokenPayload = decode(this.authenticationService.currentTokenValue.token);
    this.loggedInUser = tokenPayload.sub;
  }

  deleteUser() {
    this.accountService.deleteAccount(this.loggedInUser).subscribe(
      () => {
        this.authenticationService.logout();
      },
      (error) => {
        console.warn(error)
      },
      () => {
        this.router.navigate(['deletion-success']);
      }
    );
  }

  goBack() {
    this.router.navigate(['my-profile']);
  }
}
