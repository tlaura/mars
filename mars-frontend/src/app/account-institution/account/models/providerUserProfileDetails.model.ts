import {InstitutionListModel} from "../../institution/models/institutionList.model";

export interface ProviderUserProfileDetailsModel {
  id?: number;
  name: string;
  providerServiceName: string;
  password: string;
  email: string;
  website: string;
  phone: string;
  zipcode: number;
  city: string;
  address: string;
  ageGroupMin: number;
  ageGroupMax: number;
  types: string[];
  institutionList: InstitutionListModel[];
  newsletter: boolean;
}
