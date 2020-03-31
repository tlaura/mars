import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/auth/user";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";
import {JwtHelperService} from "@auth0/angular-jwt";
import decode from 'jwt-decode';

const BASE_URL: string = environment.BASE_URL + '/api/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public currentUser: Observable<User>;
  private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('token')));
    this.currentUser = this.currentUserSubject.asObservable();
    this.currentUser.subscribe(
      user => {
        if (this.jwtHelper.isTokenExpired(user.token)) {
          this.logout();
        }
      }
    )
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  login(email: string, password: string) {
    /*    const data = email && password ? {
          authorization: 'Basic ' + btoa(email + ':' + password),
        } : {};*/
    const data = {email: email, password: password};
    return this.http.post<any>(BASE_URL + '/login', data)
      .pipe(map(token => {
        localStorage.setItem('token', JSON.stringify(token));
        this.currentUserSubject.next(token);
        return token;
      }));
  }

  logout() {
    localStorage.removeItem('token');
    this.currentUserSubject.next(null);
  }

  isAdmin() {
    const token = localStorage.getItem('token');
    if (token) {
      const tokenPayload = decode(token);
      return tokenPayload.role === 'ROLE_ADMIN';
    }
    return false;
  }
}
