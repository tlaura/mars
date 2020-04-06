import {Injectable} from '@angular/core';
import {MessageModel} from "../models/message.model";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  url: string = environment.BASE_URL + "api/chat";

  constructor(private http: HttpClient) {
  }

  post(data: MessageModel) {
    return this.http.post<any>(this.url, data)
      .pipe(map((data: MessageModel) => {
        return data;
      }))
  }
}
