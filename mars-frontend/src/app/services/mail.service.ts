import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class MailService {

  private BASE_URL: string = "http://localhost:8080/api/confirmations/";

  constructor(private http: HttpClient) {
  }

  confirmRegistration = (token: string): Observable<any> => {
    return this.http.get(this.BASE_URL + token);
  };
}
