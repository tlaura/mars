import {Injectable} from '@angular/core';
import {AuthenticationService} from "../../services/auth/authentication.service";
import {ActivatedRouteSnapshot, CanActivate, Router} from "@angular/router";
import decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(public authenticationService: AuthenticationService, public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    // this will be passed from the route config
    // on the data property
    const expectedRole = route.data.expectedRole;
    const token = localStorage.getItem('token');
    // decode the token to get its payload
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
