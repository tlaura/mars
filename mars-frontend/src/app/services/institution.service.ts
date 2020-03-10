import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {InstitutionFormDataModel} from "../models/institutionFormData.model";
import {Observable} from "rxjs";
import {InstitutionListModel} from "../models/institutionList.model";
import {InstitutionTypeModel} from "../models/InstitutionType.model";
import {GeoLocationModel} from "../models/geoLocation.model";

@Injectable({
  providedIn: 'root'
})
export class InstitutionService {
  baseUrl = "http://localhost:8080/api/institutions/";

  constructor(private http: HttpClient) {
  }

  saveInstitution = (formData: InstitutionFormDataModel) => {
    return this.http.post(this.baseUrl, formData);
  };

  getInstitutionList = (): Observable<Array<InstitutionListModel>> => {
    return this.http.get<Array<InstitutionListModel>>(this.baseUrl);
  };

  getInstitutionTypes = (): Observable<Array<InstitutionTypeModel>> => {
    return this.http.get<Array<InstitutionTypeModel>>(this.baseUrl + "/institutionType");
  };

  getInstitutionByType = (name: string): Observable<Array<InstitutionListModel>> => {
    return this.http.get<Array<InstitutionListModel>>(this.baseUrl + "/getInstitutionsByType?type=" + name);
  };

  updateInstitutionLocation = (geoLocationModel: GeoLocationModel, id: number): Observable<void> => {
    return this.http.put<void>(this.baseUrl + "locationUpdate/" + id, geoLocationModel);
  };
}
