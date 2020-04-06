import {InstitutionDetailModel} from "../institution/models/institutionDetail.model";
import {OpeningHoursModel} from "../institution/models/openingHours.model";
import {ProviderUserDetailsModel} from "../account/models/providerUserDetails.model";

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
  openingHours: Array<OpeningHoursModel>,
  providers: Array<ProviderUserDetailsModel>,
  website,

}
