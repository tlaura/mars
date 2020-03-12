import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";

const BASE_URL: string = 'http://localhost:8080/api/user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public loggedIn$: BehaviorSubject<boolean>;

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    const headers = new HttpHeaders(
      credentials ? {authorization: 'Basic ' + btoa(credentials.userName + ':' + credentials.password),
      } : {});
    return this.http.get(BASE_URL + '/login', {headers: headers});
  }

  logout(): Observable<any> {
    console.log('logout');
    return this.http.get( 'http://localhost:8080/logout');
  }
}
