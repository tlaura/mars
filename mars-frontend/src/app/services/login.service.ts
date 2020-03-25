import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {LoggedInUserDetailsModel} from "../models/loggedInUserDetails.model";
import {environment} from "../../environments/environment";

const BASE_URL: string = environment.BASE_URL + '/api/user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public loggedIn$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);


  constructor(private http: HttpClient) {
  }

  login(credentials): Observable<any> {
    const headers = new HttpHeaders(
      credentials ? {
        authorization: 'Basic ' + btoa(credentials.userName + ':' + credentials.password),
      } : {});
    return this.http.get(BASE_URL + '/login', {headers: headers});
  }

  logout = (): Observable<any> => {
    return this.http.get(BASE_URL + '/logout');
  };

  getCurrentUser = (): LoggedInUserDetailsModel => {
    return JSON.parse(localStorage.getItem('user'));
  }

}
