import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/auth/authentication.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  profileName: string;
  isAdmin: boolean = false;
  isUserLoggedIn: boolean = false;

  constructor(public authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
    this.isUserLoggedIn = localStorage.getItem('currentUser') != null;
    this.profileName = this.authenticationService.currentUserValue.name;
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['login']);
  }
}
