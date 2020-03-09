import {OpeningHour} from "../utils/openingHour";

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

  openingHours: OpeningHour[],
  ageGroupMin: number,
  ageGroupMax: number,
  institution: string[],

  newsletter: boolean,
  termsAndConditions: boolean
}
