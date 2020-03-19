import {Injectable} from '@angular/core';
import {ProviderAccountRegisterModel} from "../models/providerAccountRegisterModel";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Institution} from "../models/institution";
import {ProviderUserProfileDetailsModel} from "../models/providerUserProfileDetails.model";
import {environment} from "../../environments/environment";
import {InstitutionListModel} from "../models/institutionList.model";
import {ProviderAccountEditDataModel} from "../models/providerAccountEditData.model";

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

  //todo replace to right service
  fetchInstitutions = (): Observable<Array<Institution>> => {
    return this.http.get<Array<Institution>>(this.BASE_URL + '/institutions/details');
  };

  fetchProviderAccountDetails = (username: string): Observable<ProviderUserProfileDetailsModel> => {
    return this.http.get<ProviderUserProfileDetailsModel>(this.BASE_URL + '/providers/' + username);
  };

  getInstitutionByType = (name: string): Observable<Array<InstitutionListModel>> => {
    return this.http.get<Array<InstitutionListModel>>(this.BASE_URL + "/providers/getInstitutionsByType?type=" + name);
  };

  fetchProviderAccountEditDetails = (loggedInUser: string) => {
    return this.http.get(this.BASE_URL + '/providers/edit/' + loggedInUser);
  };

  editProviderAccount = (data: ProviderAccountEditDataModel, loggedInUser: string): Observable<any> => {
    data.email = loggedInUser;
    return this.http.put(this.BASE_URL + '/providers/' + loggedInUser, data);
  }
}
