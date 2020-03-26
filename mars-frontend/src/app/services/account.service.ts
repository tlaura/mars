import {Injectable} from '@angular/core';
import {ProviderAccountRegisterModel} from "../models/providerAccountRegisterModel";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ProviderUserProfileDetailsModel} from "../models/providerUserProfileDetails.model";
import {environment} from "../../environments/environment";
import {ProviderAccountEditDataModel} from "../models/providerAccountEditData.model";
import {ProviderUserDetails} from "../models/providerUserDetails";
import {AccountInstitutionListModel} from "../models/accountInstitutionList.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  BASE_URL = environment.BASE_URL + '/api';

  constructor(private http: HttpClient) {
  }

  saveProviderAccount = (formData: ProviderAccountRegisterModel): Observable<any> => {
    return this.http.post(this.BASE_URL + '/providers', formData);
  };

  fetchProviderAccountDetails = (username: string): Observable<ProviderUserProfileDetailsModel> => {
    return this.http.get<ProviderUserProfileDetailsModel>(this.BASE_URL + '/providers/' + username);
  };

  getProvidersByType = (name: string): Observable<Array<AccountInstitutionListModel>> => {
    return this.http.get<Array<AccountInstitutionListModel>>(this.BASE_URL + "/providers/providersByType?type=" + name);
  };

  fetchProviderAccountEditDetails = (loggedInUser: string) => {
    return this.http.get(this.BASE_URL + '/providers/edit/' + loggedInUser);
  };

  editProviderAccount = (data: ProviderAccountEditDataModel, loggedInUser: string): Observable<any> => {
    data.email = loggedInUser;
    return this.http.patch(this.BASE_URL + '/providers/' + loggedInUser, data);
  };

  editProviderAccountDetails = (data: ProviderUserProfileDetailsModel, id: number): Observable<any> => {
    return this.http.put(this.BASE_URL + '/providers/' + id, data);
  };

  deleteInstitution(id: number, loggedInUser: string) {
    return this.http.delete(this.BASE_URL + '/providers/delete/' + loggedInUser + '/' + id);
  }

  getProviderAccountDetails = (id: number): Observable<ProviderUserDetails> => {
    return this.http.get<ProviderUserDetails>(this.BASE_URL + "/providers/provider-details/" + id);
  };

  getProviderAccountsByAgeRange = (age: number): Observable<Array<AccountInstitutionListModel>> => {
    return this.http.get<Array<AccountInstitutionListModel>>(this.BASE_URL + "/providers/ageRange?age=" + age);
  };
}
