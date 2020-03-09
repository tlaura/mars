import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const BASE_URL: string = 'http://localhost:8080/api/user';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  authenticate(credentials): Observable<any> {
    const headers = new HttpHeaders(
      credentials ? {authorization: 'Basic ' + btoa(credentials.userName + ':' + credentials.password),
      } : {});
    return this.http.get(BASE_URL + '/login', {headers: headers});
  }
}
