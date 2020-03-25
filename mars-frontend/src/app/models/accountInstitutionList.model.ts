import {InstitutionDetailModel} from "./institutionDetail.model";

export interface AccountInstitutionListModel {
  accountType: string,
  id: number,
  providerServiceName: string,
  name: string,
  email: string,
  types: Array<String>,
  phone: string,
  city: string,
  address: string,
  ageGroupMin: number,
  ageGroupMax: number,
  newsletter: boolean,
  latitude: number,
  longitude: number,
  institutions: Array<InstitutionDetailModel>,
}
