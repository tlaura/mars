import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InstitutionListModel} from "../models/institutionList.model";
import {InstitutionTypeModel} from "../models/InstitutionType.model";
import {InstitutionDetailModel} from "../models/institutionDetail.model";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class InstitutionService {
  private BASE_URL = environment.BASE_URL + "/api/institutions/";

  constructor(private http: HttpClient) {
  }

  //TODO: new model
  saveInstitution = (formData: InstitutionDetailModel) => {
    return this.http.post(this.BASE_URL, formData);
  };

  getInstitutionList = (): Observable<Array<InstitutionListModel>> => {
    return this.http.get<Array<InstitutionListModel>>(this.BASE_URL);
  };

  getInstitutionTypes = (): Observable<Array<InstitutionTypeModel>> => {
    return this.http.get<Array<InstitutionTypeModel>>(this.BASE_URL + "institutionType");
  };


  getInstitutionDetail = (id: number): Observable<InstitutionDetailModel> => {
    return this.http.get<InstitutionDetailModel>(this.BASE_URL + id);
  };

  import = (importData: any) => {
    return this.http.post(this.BASE_URL + 'import', importData)
  };
}
