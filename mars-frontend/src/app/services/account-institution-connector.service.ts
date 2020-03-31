import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountInstitutionListModel} from "../models/accountInstitutionList.model";
import {LocationRangeModel} from "../models/locationRange.model";

@Injectable({
  providedIn: 'root'
})
export class AccountInstitutionConnectorService {

  private BASE_URL: string = environment.BASE_URL + "/api/connectors/";

  constructor(private http: HttpClient) {
  }

  getAllAccountConnections = (): Observable<Array<AccountInstitutionListModel>> => {
    return this.http.get<Array<AccountInstitutionListModel>>(this.BASE_URL);
  };

  getAccountsByRange = (locationRange: LocationRangeModel): Observable<Array<AccountInstitutionListModel>> => {
    return this.http.get<Array<AccountInstitutionListModel>>(this.BASE_URL + "listByRange?range=" + locationRange.range + "&lng=" + locationRange.longitude + "&lat=" + locationRange.latitude);
  }
}
