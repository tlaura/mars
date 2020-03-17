import {Institution} from "./institution";

export interface ProviderAccountRegisterModel {
  providerServiceName: string,
  name: string,
  password: string,
  email: string,
  phone: string,

  zipcode: number,
  city: string,
  address: string,

  type: Array<string>,

  ageGroupMin: number,
  ageGroupMax: number,
  institutions: Array<Institution>,

  newsletter: boolean,
}
