import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {InstitutionFormDataModel} from "../models/institutionFormData.model";
import {InstitutionListItemModel} from "../models/institutionListItem.model";
import {Observable} from "rxjs";

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

  loadInstitutions(): Observable<Array<InstitutionListItemModel>> {
    return this.http.get<Array<InstitutionListItemModel>>(this.baseUrl);
  }
}
