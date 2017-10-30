import { Component, OnInit, OnDestroy, HostListener, ViewChild } from '@angular/core';
import { RegistrationService } from "../services/registration.service";
import { EnumProvider } from "../../commons/util/enum-provider";
import { UtilMethods } from "../../commons/util/util-methods.service";
import { PersonalData } from "../model/personalData/personal-data";
import { DateUtils } from "../../commons/util/date-utils";
import { Address } from "../model/address/address";
import { Employee } from '../model/employee/employee';
import { Parent } from '../menu/model/parent/parent';
import { Message, InputMaskModule } from "primeng/primeng";
import { UserRegistrationDTO } from '../model/user/userRegistrationDTO';
import { ErrorHandler } from '../../commons/util/error-handler';
import { Router } from '@angular/router';

const NUMBER_OF_ADDRESSES = 3;
const NIP_WEIGHT_NUMBERS = [6, 5, 7, 2, 3, 4, 5, 6, 7];

@Component({
  selector: 'registration',
  templateUrl: './registration.component.html',
  providers: [RegistrationService, EnumProvider, UtilMethods, DateUtils]
})

export class RegistrationComponent implements OnInit, OnDestroy {

  constructor(private registrationService: RegistrationService, private enumProvider: EnumProvider,
    private utilMethods: UtilMethods, private dateUtils: DateUtils, private router: Router) { }

  @ViewChild('registrationForm') form;

  private personalData: PersonalData;
  private user: UserRegistrationDTO;
  private employee: Employee;
  private parent: Parent;
  private addresses = [];
  private userRoles = EnumProvider.ROLES;
  private employeeRoles = EnumProvider.EMPLOYEE_ROLES;
  private genders = EnumProvider.GENDERS;
  private numberOfAddresses = 1;
  private isNipCorrect: boolean;
  private isAdressesArrayValid: boolean[] = [];
  private isAllAddressesValid: boolean;
  private msgs: Message[] = [];

  private init() {
    this.personalData = new PersonalData();
    this.user = new UserRegistrationDTO();
    this.employee = new Employee();
    this.parent = new Parent();
    this.addresses = [];
  }

  ngOnInit() {
    this.init();
    this.translateEnumsToDropdown();
    this.getDataFromSessionStorage();
  }

  translateEnumsToDropdown() {
    this.userRoles = this.enumProvider.translateToDropdown(this.userRoles);
    this.employeeRoles = this.enumProvider.translateToDropdown(this.employeeRoles);
    this.genders = this.enumProvider.translateToDropdown(this.genders);
  }

  getDataFromSessionStorage() {
    if (sessionStorage.getItem('personalData'))
      this.personalData = JSON.parse(sessionStorage.getItem('personalData'));

    if (sessionStorage.getItem('user'))
      this.user = JSON.parse(sessionStorage.getItem('user'));

    if (sessionStorage.getItem('employee'))
      this.employee = JSON.parse(sessionStorage.getItem('employee'));

    if (sessionStorage.getItem('parent'))
      this.parent = JSON.parse(sessionStorage.getItem('parent'));

    if (sessionStorage.getItem('isAllAddressesValid'))
      this.isAllAddressesValid = JSON.parse(sessionStorage.getItem('isAllAddressesValid'));
    if (sessionStorage.getItem('isNipCorrect'))
      this.isNipCorrect = JSON.parse(sessionStorage.getItem('isNipCorrect'));

    if (sessionStorage.getItem('addresses')) {
      this.addresses = JSON.parse(sessionStorage.getItem('addresses'));
    }
    else
      this.addNewAddressToAddressesList();

    this.nipIsValid();
  }

  firstLetterUpperCaseAndGenerateUsername(value: string) {
    this.generateUsername();
    return this.firstLetterUpperCase(value);
  }

  firstLetterUpperCase(value: string) {
    return this.utilMethods.firstLetterUpperCase(value);
  }

  private generateUsername() {
    if (this.personalData.name !== null && this.personalData.surname !== null)
      this.user.username = this.personalData.name.toLowerCase() + this.personalData.surname.toLowerCase();
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
    sessionStorage.removeItem('personalData');

  }

  addAddress() {
    this.numberOfAddresses++;
  }

  addNewAddressToAddressesList(): void {
    this.addresses.push(new Address());
  }

  deleteAddress(id: number): void {
    const index: number = id;
    if (index !== -1) {
      this.addresses.splice(index, 1);
      this.isAdressesArrayValid.splice(index, 1);
    }
    this.addressesValidate();
  }

  resetAddress(id: number): void {
    const index: number = id;
    if (index !== -1) {
      this.addresses[index] = new Address();
    }
    this.addressesValidate();
  }

  addressIsValid(isValid: boolean, id: number): void {
    this.isAdressesArrayValid[id] = isValid;
    this.addressesValidate();
  }

  addressesValidate() {
    let isTrue = true;
    for (let isValid of this.isAdressesArrayValid)
      if (isValid == false) isTrue = false;

    this.isAllAddressesValid = isTrue;

  }

  numberOfAddressesExhausted(): boolean {
    return this.addresses.length < NUMBER_OF_ADDRESSES;
  }

  whoIsThis() {
    switch (this.user.role) {
      case 'ROLE_ADMIN':
        this.employee = new Employee();
        this.employee.employeeRole = 'ADMIN';
        break;
      case 'ROLE_EMPLOYEE':
        this.employee = new Employee();
        this.employee.employeeRole = '';
        break;
      case 'ROLE_EMPLOYEE':
        this.parent = new Parent();
        break;
    }
  }

  resetForm() {
    this.form.reset();
  };

  nipIsValid() {
    let sum = 0;
    if (this.employee.nip) {
      if (this.employee.nip.length > 9) {
        for (let i = 0; i < 9; i++) {
          sum += (+this.employee.nip.charAt(i) * NIP_WEIGHT_NUMBERS[i]);
        }
        let expected: number = +this.employee.nip.charAt(9);
        let actual: number = sum % 11;

        if (actual === expected)
          this.isNipCorrect = true;

        else this.isNipCorrect = false;
      }
      else this.isNipCorrect = false;
    } 
    else this.isNipCorrect = true;

  }
  onChange() {
    this.whetherMan();
  }
  submit() {
    this.createUser();
  }

  createUser() {
    this.registrationService.createUser(this.user)
      .subscribe(data => {
        this.personalData.user = { id: data.id, value: "" };
        this.createPersonalData();
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }

  createPersonalData() {
    this.registrationService.createPersonalData(this.personalData)
      .subscribe(data => {
        this.personalData = data;
        this.createAddress();
        this.createOfTheSelectedRole();
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }

  createAddress() {
    for (let i = 0; i < this.addresses.length; i++) {
      this.addresses[i].personalData = { id: this.personalData.id, value: "" };
      this.registrationService.createAddress(this.addresses[i])
        .subscribe(data => {
          this.addresses[i] = data;
          this.msgs = [];
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
  }

  createOfTheSelectedRole() {
    switch (this.user.role) {
      case 'ROLE_ADMIN':
        this.createAdmin();
        break;
      case 'ROLE_EMPLOYEE':
        this.createEmployee();
        break;
      case 'ROLE_PARENT':
        this.createParent();
        break;
    }
  }

  createAdmin() {
    this.employee.personalData = { id: this.personalData.id, value: "" };
    this.registrationService.createAdmin(this.employee)
      .subscribe(data => {
        this.resetForm();
        this.resetAddresses();
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }

  createEmployee() {
    this.employee.personalData = { id: this.personalData.id, value: "" };
    this.registrationService.createEmployee(this.employee)
      .subscribe(data => {
        this.resetForm();
        this.resetAddresses();
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }

  createParent() {
    this.parent.personalData = { id: this.personalData.id, value: "" };
    this.registrationService.createParent(this.parent)
      .subscribe(data => {
        this.resetForm();
        this.resetAddresses();
        this.router.navigateByUrl('admin/addUser');
        this.msgs = [];
      }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
  }

  resetAddresses() {
    this.addresses = [];
    this.addNewAddressToAddressesList();
  }

  @HostListener('window:beforeunload', ['$event'])
  beforeUnloadHander(event) {
    this.saveAllDataToSessionStorage();
  }

  ngOnDestroy() {
    this.saveAllDataToSessionStorage();
  }

  saveAllDataToSessionStorage() {
    this.savePersonalDataToSessionStorage();
    this.saveUserToSessionStorage();
    this.saveEmployeeToSessionStorage();
    this.saveParentToSessionStorage();
    this.saveAddressesToSessionStorage();
    this.saveIsAllAddressesValidToSessionStorage();

  }

  savePersonalDataToSessionStorage() {
    let personalData = JSON.stringify(this.personalData);
    sessionStorage.setItem('personalData', personalData);
  }

  saveUserToSessionStorage() {
    let user = JSON.stringify(this.user);
    sessionStorage.setItem('user', user);
  }

  saveEmployeeToSessionStorage() {
    let employee = JSON.stringify(this.employee);
    sessionStorage.setItem('employee', employee);
  }

  saveParentToSessionStorage() {
    let parent = JSON.stringify(this.parent);
    sessionStorage.setItem('parent', parent);
  }

  saveAddressesToSessionStorage() {
    let addresses = JSON.stringify(this.addresses);
    sessionStorage.setItem('addresses', addresses);
  }

  saveIsAllAddressesValidToSessionStorage() {
    let isAllAddressesValid = JSON.stringify(this.isAllAddressesValid);
    sessionStorage.setItem('isAllAddressesValid', isAllAddressesValid);
  }

  saveIsNipCorrectToSessionStorage() {
    let isNipCorrect = JSON.stringify(this.isNipCorrect);
    sessionStorage.setItem('isAllAddressesValid', isNipCorrect);
  }


}