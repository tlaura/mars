import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/auth/authentication.service";

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
      user => this.isUserLoggedIn = user != null
    );
    this.authenticationService.currentUser.subscribe(
      user => this.profileName = user?.name
    );
    this.authenticationService.currentUser.subscribe(
      user => this.isAdmin = user?.role == 'ADMIN'
    )
  }

  ngOnInit() {

  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['login']);
  }
}
