import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {InstitutionFormDataModel} from "../models/institutionFormData.model";
import {Observable} from "rxjs";
import {InstitutionListModel} from "../models/institutionList.model";

@Injectable({
  providedIn: 'root'
})
export class InstitutionService {
  baseUrl = "http://localhost:8080/api/institutions";

  constructor(private http: HttpClient) {
  }

  saveInstitution(formData: InstitutionFormDataModel) {
    return this.http.post(this.baseUrl, formData);
  }

  getInstitutionList(): Observable<Array<InstitutionListModel>> {
    return this.http.get<Array<InstitutionListModel>>(this.baseUrl);
  }
}
