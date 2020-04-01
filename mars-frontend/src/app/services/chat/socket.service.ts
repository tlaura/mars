import {Injectable} from '@angular/core';
import {Message} from "../../models/chat/message";
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

  post(data: Message) {
    return this.http.post<any>(this.url, data)
      .pipe(map((data: Message) => {
        return data;
      }))
  }


}
