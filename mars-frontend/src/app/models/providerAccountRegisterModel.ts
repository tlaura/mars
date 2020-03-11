import {OpeningHours} from "./openingHours";
import {Institution} from "./institution";

export interface ProviderAccountRegisterModel {
  name: string,

  username: string,
  password: string,
  email: string,
  phone: string,

  zipcode: number,
  city: string,
  address: string,

  type: string,

  openingHours: OpeningHours[],
  ageGroupMin: number,
  ageGroupMax: number,
  institutions: Institution[],

  newsletter: boolean,
  termsAndConditions: boolean
}
