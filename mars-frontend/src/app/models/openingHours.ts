import {Time} from "@angular/common";

export interface OpeningHours {
  weekDay: string,
  openingTime: Time,
  closingTime: Time,
}
