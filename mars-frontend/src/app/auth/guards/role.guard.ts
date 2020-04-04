import {Injectable} from '@angular/core';
import {AuthenticationService} from "../services/authentication.service";
import {ActivatedRouteSnapshot, CanActivate, Router} from "@angular/router";
import decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(public authenticationService: AuthenticationService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRole = route.data.expectedRole;
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigate(['login']);
      return false;
    }
    const tokenPayload = decode(token);
    if (
      !this.authenticationService.isAuthenticated() ||
      tokenPayload.role !== expectedRole
    ) {
      this.router.navigate(['login']);
      return false;
    }
    //TODO: navigate to admin UI
    return true;
  }
}
