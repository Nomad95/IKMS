import { Component, OnInit, OnDestroy, HostListener, ViewChild } from '@angular/core';
import { RegistrationService } from "../../sharedModule/services/registration.service";
import { EnumProvider } from "../../commons/util/enum-provider";
import { UtilMethods } from "../../commons/util/util-methods.service";
import { PersonalData } from "../model/personalData/personal-data";
import { DateUtils } from "../../commons/util/date-utils";
import { Address } from "../model/address/address";
import { Employee } from '../model/employee/employee';
import { Parent } from '../menu/model/parent/parent';
import { Message, InputMaskModule, MenuItem } from "primeng/primeng";
import { UserRegistrationDTO } from '../model/user/userRegistrationDTO';
import { ErrorHandler } from '../../commons/util/error-handler';
import { Router } from '@angular/router';
import { BreadMaker } from "../../commons/util/bread-maker";
import { RegistrationDto } from "../model/user/registration-dto";
import { CommonMessages } from "../../commons/util/common-messages";

const NUMBER_OF_ADDRESSES = 3;
const NIP_WEIGHT_NUMBERS = [6, 5, 7, 2, 3, 4, 5, 6, 7];

@Component({
    selector: 'registration',
    templateUrl: './registration.component.html',
    providers: [EnumProvider, UtilMethods, DateUtils]
})

export class RegistrationComponent implements OnInit, OnDestroy {
    
    constructor(private registrationService: RegistrationService,
                private enumProvider: EnumProvider,
                private utilMethods: UtilMethods,
                private dateUtils: DateUtils,
                private router: Router) {
    }
    
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
    private isSubmiting: boolean = false;
    private isAdressesArrayValid: boolean[] = [];
    private isAllAddressesValid: boolean;
    private msgs: Message[] = [];
    private items: MenuItem[];
    
    private init() {
        this.personalData = new PersonalData();
        this.user = new UserRegistrationDTO();
        this.employee = new Employee();
        this.parent = new Parent();
        this.addresses = [];
    }
    
    ngOnInit() {
        this.items = BreadMaker.makeBreadcrumbs("Użytkownicy", "Dodaj");
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
    
    firstLetterUpperCase(value: string) {
        return this.utilMethods.firstLetterUpperCase(value);
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
        this.isSubmiting = true;
        this.createUser();
    }
    
    createUser() {
        let registrationDto = new RegistrationDto();
        registrationDto.personalData = this.personalData;
        registrationDto.userRegistrationDto = this.user;
        registrationDto.addressess = this.createAddress();
        registrationDto = this.createOfTheSelectedRole(registrationDto);
        console.log(registrationDto);
        this.registrationService.createUser(registrationDto)
            .subscribe(data => {
                this.msgs = CommonMessages.userCreationSuccess();
            }, err => {
                this.msgs = CommonMessages.userCreationError();
                this.isSubmiting = false;
            });
    }
    
    createPersonalData() {
        this.registrationService.createPersonalData(this.personalData)
        .subscribe(data => {
            this.personalData = data;
            this.createAddress();
            this.msgs = [];
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isSubmiting = false;
        });
    }
    
    createAddress(): any[]{
        let addressessList = [];
        for (let i = 0; i < this.addresses.length; i++) {
            this.addresses[i].personalData = {id: this.personalData.id, value: ""};
            addressessList.push(this.addresses[i]);
        }
        return addressessList;
    }
    
    createOfTheSelectedRole(registrationDto: RegistrationDto): RegistrationDto {
        switch (this.user.role) {
            case 'ROLE_ADMIN':
                return this.createAdmin(registrationDto);
            case 'ROLE_EMPLOYEE':
                return this.createEmployee(registrationDto);
            case 'ROLE_PARENT':
                return this.createParent(registrationDto);
        }
    }
    
    createAdmin(registrationDto: RegistrationDto): RegistrationDto {
        this.employee.personalData = {id: this.personalData.id, value: ""};
        registrationDto.employee = this.employee;
        return registrationDto;
    }
    
    createEmployee(registrationDto: RegistrationDto): RegistrationDto {
        this.employee.personalData = {id: this.personalData.id, value: ""};
        registrationDto.employee = this.employee;
        return registrationDto;
    }
    
    createParent(registrationDto: RegistrationDto): RegistrationDto {
        this.parent.personalData = {id: this.personalData.id, value: ""};
        registrationDto.parent = this.parent;
        return registrationDto;
    }
    
    resetForm() {
        this.isSubmiting = false;
        this.form.reset();
    };
    
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
