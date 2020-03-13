import {Injectable} from '@angular/core';
import {ProviderAccountRegisterModel} from "../models/providerAccountRegisterModel";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Institution} from "../models/institution";
import {ProviderUserProfileDetailsModel} from "../models/providerUserProfileDetails.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  BASE_URL = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
  }

  saveProviderAccount = (formData: ProviderAccountRegisterModel): Observable<any> => {
    return this.http.post(this.BASE_URL + '/providers', formData);
  }

  fetchInstitutions = (): Observable<Array<Institution>> => {
    return this.http.get<Array<Institution>>(this.BASE_URL + '/institutions/details');
  }

  fetchProviderAccountDetails = (username: string): Observable<ProviderUserProfileDetailsModel> => {
    return this.http.get<ProviderUserProfileDetailsModel>(this.BASE_URL + '/' + 'username');
  }
}
