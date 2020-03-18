import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {EmailModel} from "../models/email.model";

@Injectable({
  providedIn: 'root'
})
export class MailService {

  private BASE_URL: string = "http://localhost:8080/api/mails/";

  constructor(private http: HttpClient) {
  }

  confirmRegistration = (token: string): Observable<any> => {
    return this.http.get(this.BASE_URL + "confirmation/" + token);
  };

  sendEmail = (mailModel: EmailModel): Observable<any> => {
    return this.http.post(this.BASE_URL, mailModel);
  }
}
