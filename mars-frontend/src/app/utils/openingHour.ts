import {Time} from "@angular/common";

export interface OpeningHour {
  weekDay: string,
  openingTime: Time,
  closingTime: Time,
}
