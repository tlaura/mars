import {OpeningHoursModel} from "./openingHours.model";
import {ProviderUserShortDetailsModel} from "../../account/models/providerUserShortDetails.model";

export interface InstitutionDetailModel {
  id: number,
  name: string,
  zipcode: number,
  city: string,
  address: string,
  email: string,
  description: string,
  longitude: number,
  latitude: number,
  website: string,
  phone: string,
  openingHours: Array<OpeningHoursModel>
  providers: Array<ProviderUserShortDetailsModel>
}
