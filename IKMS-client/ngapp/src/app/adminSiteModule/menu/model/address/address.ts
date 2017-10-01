export class Address{
    id: number;
    addressType: string;
    personalData: any;
    street: string;
    streetNumber: number;
    houseNumber: string;
    postCode: string;
    city: string;
    community: string;
    county: string;
    voivodeship: string;
    country: string;
  
  
  constructor() {
    this.id = -1;
    this.addressType = '';
    this.personalData = {};
    this.street = '';
    this.streetNumber = -1;
    this.houseNumber = '';
    this.postCode = '';
    this.city = '';
    this.community = '';
    this.county = '';
    this.voivodeship = '';
    this.country = '';
  }
}
