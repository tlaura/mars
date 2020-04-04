import {Institution} from "../../institution/models/institution";

export interface ProviderAccountRegisterModel {
  providerServiceName: string,
  password: string,
  type: Array<string>,
  ageGroupMin: number,
  ageGroupMax: number,

  zipcode: number,
  city: string,
  address: string,

  institutions: Array<Institution>,

  name: string,
  website: string,
  email: string,
  phone: string,

  newsletter: boolean
}
