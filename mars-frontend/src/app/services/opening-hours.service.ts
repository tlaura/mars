import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {OpeningHoursModel} from "../models/openingHours.model";

@Injectable({
  providedIn: 'root'
})
export class OpeningHoursService {

  private BASE_URL: string = environment.BASE_URL + "/api/openingHours/";

  constructor(private http: HttpClient) {
  }

  getOpeningHoursByInstitutionId = (id: number): Observable<Array<OpeningHoursModel>> => {
    return this.http.get<Array<OpeningHoursModel>>(this.BASE_URL + id);
  };

}
