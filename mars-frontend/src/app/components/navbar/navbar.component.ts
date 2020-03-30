import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/auth/authentication.service";
import decode from 'jwt-decode';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  profileName: string = '';
  isAdmin: boolean = false;
  isUserLoggedIn: boolean = false;

  constructor(public authenticationService: AuthenticationService, private router: Router) {
    this.authenticationService.currentUser.subscribe(
      () => this.isUserLoggedIn = authenticationService.isAuthenticated()
    );
    this.authenticationService.currentUser.subscribe(
      token => {
        if (token) {
          const tokenPayload = decode(token);
          this.profileName = tokenPayload?.name;
        }
      }
    );
    this.authenticationService.currentUser.subscribe(
      token => {
        if (token) {
          const tokenPayload = decode(token);
          this.isAdmin = tokenPayload?.role == 'ADMIN';
        }
      }
    );
  }

  ngOnInit() {

  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['login']);
  }
}
