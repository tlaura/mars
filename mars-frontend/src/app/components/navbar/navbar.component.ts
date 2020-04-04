import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../auth/services/authentication.service";
import {UserModel} from "../../chat/models/user.model";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  profileName: string = '';
  isAdmin: boolean = false;
  isUserLoggedIn: boolean = false;
  currentUser: UserModel;

  constructor(public authenticationService: AuthenticationService, private router: Router) {
    this.authenticationService.currentToken.subscribe(
      () => {
        this.isUserLoggedIn = authenticationService.isAuthenticated();
        this.isAdmin = authenticationService.isAdmin();
        this.currentUser = this.authenticationService.getCurrentUser();
      });
  }

  ngOnInit() {
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['login']);
  }
}
