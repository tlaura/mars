import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {TokenModel} from "../models/token.model";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";
import {JwtHelperService} from "@auth0/angular-jwt";
import decode from 'jwt-decode';
import {UserModel} from "../../chat/models/user.model";

const BASE_URL: string = environment.BASE_URL + '/api/user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  public currentToken: Observable<TokenModel>;
  public currentUser: Observable<UserModel>;
  private currentTokenSubject: BehaviorSubject<TokenModel>;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) {
    this.currentTokenSubject = new BehaviorSubject<TokenModel>(JSON.parse(localStorage.getItem('token')));
    this.currentToken = this.currentTokenSubject.asObservable();
    this.currentToken.subscribe(
      token => {
        if (token) {
          if (this.jwtHelper.isTokenExpired(token.token)) {
            this.logout();
          }
        }
      }
    )
  }

  public get currentTokenValue(): TokenModel {
    return this.currentTokenSubject.value;
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
        this.currentTokenSubject.next(token);
        return token;
      }));
  }

  logout() {
    localStorage.removeItem('token');
    this.currentTokenSubject.next(null);
  }

  isAdmin() {
    const token = localStorage.getItem('token');
    if (token) {
      const tokenPayload = decode(token);
      return tokenPayload.role === 'ROLE_ADMIN';
    }
    return false;
  }

  getCurrentUser(): UserModel {
    const token = localStorage.getItem('token');
    let user: UserModel = null;
    if (token) {
      const tokenPayload = decode(token);
      user = {
        email: tokenPayload.sub,
        name: tokenPayload.name
      };
    }
    return user;
  }

  getRole = (): string => {
    const token = localStorage.getItem('token');
    if (token) {
      const tokenPayload = decode(token);
      return tokenPayload.role;
    }
  };
}
