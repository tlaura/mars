import {OpeningHours} from "./openingHours";

export interface Institution {
  id: number,

  name: string,
  email: string,
  website: string,
  phone: string,

  zipcode: number,
  city: string,
  address: string,

  openingHours: OpeningHours[],
  description: string
}
