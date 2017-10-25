import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { RegistrationService } from "../services/registration.service";
import { User } from "../model/user/user";
import { EnumProvider } from "../../commons/util/enum-provider";
import { RegistrationDataToSessionStorage } from "../model/user/registrationDataToSessionStorage";
import { UtilMethods } from "../../commons/util/util-methods.service";
import { PersonalData } from "../model/personalData/personal-data";
import { DateUtils } from "../../commons/util/date-utils";
import { Address } from "../model/address/address";

const NUMBER_OF_ADDRESSES = 3;

@Component({
  selector: 'registration',
  templateUrl: './registration.component.html',
  providers: [RegistrationService, EnumProvider, UtilMethods, DateUtils]
})

export class RegistrationComponent implements OnInit, OnDestroy {

  constructor(private registrationService: RegistrationService,
    private enumProvider: EnumProvider, private utilMethods: UtilMethods, private dateUtils: DateUtils) { }

  private personalData: PersonalData;
  private addresses = [];
  private roles = EnumProvider.ROLES;
  private genders = EnumProvider.GENDERS;
  private numberOfAddresses = 1;

  private init() {
    this.personalData = new PersonalData();
  }

  ngOnInit() {
    this.init();
    this.addNewAddressToAddressesList();
    if (sessionStorage.getItem('personalData')) {
      this.personalData = JSON.parse(sessionStorage.getItem('personalData'));
    }
    this.roles = this.enumProvider.translateToDropdown(this.roles);
    this.genders = this.enumProvider.translateToDropdown(this.genders);
  }

  firstLetterUpperCaseAndGenerateUsername(value: string) {
    this.generateUsername();
    return this.firstLetterUpperCase(value);
  }

  firstLetterUpperCase(value: string) {
    return this.utilMethods.firstLetterUpperCase(value);
  }

  private generateUsername() {
    this.personalData.user.username = this.personalData.name.toLowerCase() + this.personalData.surname.toLowerCase();
  }

  whetherMan() {
    if (this.personalData.gender == 'MAN')
      this.personalData.familyName = this.personalData.surname;
    else this.personalData.familyName = '';
  }

  onDateSelected(event) {
    this.personalData.dateOfBirth = DateUtils.formatDate(event);
  }

  addUser() {
    console.log(this.personalData);
    sessionStorage.removeItem('personalData');
    this.resetForm();
  }

  addAddress(){
    this.numberOfAddresses++;
  }

  addNewAddressToAddressesList(): void {
    this.addresses.push(new Address());
    console.log(this.addresses);
  }

  deleteAddress(id: number): void{
    const index: number = id;
    if (index !== -1) {
      this.addresses.splice(index,1);
    }
  }

  numberOfAddressesExhausted(): boolean{
    return this.addresses.length < NUMBER_OF_ADDRESSES;
  }

  resetForm() {
    this.init();
    console.log(this.addresses);
  };

  @HostListener('window:beforeunload', ['$event'])
  beforeUnloadHander(event) {
    let value = JSON.stringify(this.personalData);
    sessionStorage.setItem('personalData', value);
  }

  ngOnDestroy() {
    let value = JSON.stringify(this.personalData);
    sessionStorage.setItem('personalData', value);
  }


}
