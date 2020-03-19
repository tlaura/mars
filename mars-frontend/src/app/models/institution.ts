import {OpeningHoursModel} from "./openingHours.model";

export interface Institution {
  id: number,

  name: string,
  email: string,
  website: string,
  phone: string,

  zipcode: number,
  city: string,
  address: string,

  openingHours: OpeningHoursModel[],
  description: string
}
