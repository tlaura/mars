import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment.local";

@Injectable({
  providedIn: 'root'
})
export class GeocodeService {

  private BASE_URL: string = "https://maps.googleapis.com/maps/api/geocode/json?address=";

  constructor(private http: HttpClient) {
  }

  getLocations = (address: string): Observable<any> => {
    return this.http.get(this.BASE_URL + address + "&key=" + environment.apiKey)
  };
}
