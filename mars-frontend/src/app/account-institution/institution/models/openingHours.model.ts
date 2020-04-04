import {Time} from "@angular/common";

export interface OpeningHoursModel {
  weekDay: string,
  openingTime: Time,
  closingTime: Time,
}
