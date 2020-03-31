import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services/auth/authentication.service";
import decode from 'jwt-decode';

@Component({
  selector: 'app-confirm-deletion',
  templateUrl: './confirm-deletion.component.html',
  styleUrls: ['./confirm-deletion.component.css']
})
export class ConfirmDeletionComponent implements OnInit {
  loggedInUser: string;

  constructor(private router: Router, private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    if (!this.authenticationService.currentUserValue.token) {
      this.router.navigate(['login']);
    }
    const tokenPayload = decode(this.authenticationService.currentUserValue.token);
    this.loggedInUser = tokenPayload.sub;
  }

  deleteUser() {

  }

  goBack() {
    this.router.navigate(['my-profile']);
  }
}
