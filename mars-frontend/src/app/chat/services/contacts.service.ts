import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ContactCreationModel} from "../models/contactCreation.model";
import {AuthenticationService} from "../../auth/services/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class ContactsService {
  BASE_URL = environment.BASE_URL + '/api';

  contactSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');
  messageSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');


  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  addContact = (email: string): Observable<any> => {
    let contactCreation: ContactCreationModel = {
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
