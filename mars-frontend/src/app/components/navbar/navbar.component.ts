import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../services/login.service";
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
    const isUserLoggedIn = localStorage.getItem('user') === 'true';
    this.loginService.loggedIn$ = new BehaviorSubject(isUserLoggedIn);
  }

  logout() {
    this.loginService.logout().subscribe(
      () => {
        localStorage.clear();
        this.loginService.loggedIn$.next(false);
        this.router.navigate(['/login']);
      }
    );
  }

}
