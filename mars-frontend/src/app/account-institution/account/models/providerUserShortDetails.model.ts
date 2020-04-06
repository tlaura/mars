export interface ProviderUserShortDetailsModel {

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
}
