import {InstitutionListModel} from "./institutionList.model";

export interface ProviderUserProfileDetailsModel {
  id?: number;
  name: string;
  username: string;
  password: string;
  email: string;
  phone: string;
  zipcode: string;
  city: string;
  address: string;
  institutionList: InstitutionListModel[];
  // openinghours: OpeningHours[];
}
