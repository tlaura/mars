import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ContactCreation} from "../../models/chat/contactCreation";
import {AuthenticationService} from "../auth/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class ContactsService {
  BASE_URL = environment.BASE_URL + '/api';

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  addContact = (email: string): Observable<any> => {
    let contactCreation: ContactCreation = {
      fromEmail: this.authenticationService.getCurrentUser().email,
      toEmail: email
    };
    return this.http.post(this.BASE_URL + '/contacts', contactCreation);
  };

  fetchContacts = (userEmail: string): Observable<any> => {
    return this.http.get(this.BASE_URL + '/contacts?email=' + userEmail);
  };

  fetchMessages = (from: string, to: string): Observable<any> => {
    return this.http.get(this.BASE_URL + '/contacts/history?fromEmail=' + from + '&toEmail=' + to);
  }
}
