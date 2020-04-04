import {InstitutionListModel} from "../../institution/models/institutionList.model";

export interface ProviderUserDetails {
  id: number,
  providerServiceName: string,
  name: string,
  email: string,
  phone: string,
  zipcode: number,
  city: string,
  address: string,
  ageGroupMin: number,
  ageGroupMax: number,
  types: Array<string>,
  institutions: Array<InstitutionListModel>,
  newsletter: boolean,
  longitude: string,
  latitude: string,
}
