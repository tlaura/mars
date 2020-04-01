import {Injectable} from '@angular/core';
import {ProviderAccountRegisterModel} from "../models/providerAccountRegisterModel";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ProviderUserProfileDetailsModel} from "../models/providerUserProfileDetails.model";
import {environment} from "../../environments/environment";
import {ProviderUserDetails} from "../models/providerUserDetails";
import {AccountInstitutionListModel} from "../models/accountInstitutionList.model";
import {PasswordChangeDetailsModel} from "../models/passwordChangeDetails.model";
import {NormalAccountRegisterModel} from "../models/normalAccountRegisterModel";

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

  updatePassword(passwordChangeDetails: PasswordChangeDetailsModel) {
    return this.http.patch(this.BASE_URL + '/providers/password-change', passwordChangeDetails);
  }

  saveNormalAccount = (formData: NormalAccountRegisterModel): Observable<any> => {
    return this.http.post(this.BASE_URL + '/user', formData);
  };

  deleteAccount(loggedInUser: string) {
    return this.http.delete(this.BASE_URL + '/user/delete/' + loggedInUser);
  }
}
