import { Component, OnInit } from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import { AddressService } from "../../../sharedModule/services/address.service";
import { PersonalDataService } from "../../../sharedModule/services/personal-data.service";
import { ActivatedRoute } from "@angular/router";
import { Employee } from "../../model/employee/employee";
import {PersonalData} from "../../model/personalData/personal-data";
import {Address} from "../../model/address/address";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";

@Component({
  selector: 'employee-detail',
  templateUrl: './employee-detail.component.html',
  providers: []
})
export class EmployeeDetailComponent implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private addressService: AddressService,
        private personalDataService: PersonalDataService,
        private route: ActivatedRoute){}

    private employeeId: number;
    private personalDataId: number;
    private employee: Employee;
    private personalData: PersonalData;
    private addresses: Address[];
    private editAddressId = -1;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];

    private displayEmployeeEditModal = false;
    private displayPersonalDataEditModal = false;
    private displayAddressEditModal = false;

    private displayAddressCreateModal = false;

    ngOnInit(){
        this.employeeId = this.route.snapshot.params['id'];
        this.personalDataId = this.route.snapshot.queryParams['personalDataId'] || -1;
        this.items = BreadMaker.makeBreadcrumbs("Pracownicy","Lista pracowników","Podgląd");

        this.getEmployee();
        this.getPersonalData();
        this.getAddresses();
    }

    getEmployee(){
        this.isLoading = true;
        this.employeeService.getEmployee(this.employeeId)
            .subscribe( data => {
                this.employee = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }

    getPersonalData(){
        this.personalDataService.getPersonalData(this.personalDataId)
            .subscribe( data => {
                this.personalData = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }

    getAddresses(){
        this.addressService.getAddressesByPersonalDataId(this.personalDataId)
            .subscribe( data => {
                this.addresses = data;
                this.msgs = [];
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            });
    }

    showEmployeeEditModal(): void{
        this.displayEmployeeEditModal = true;
    }

    showPersonalDataEditModal(): void{
        this.displayPersonalDataEditModal = true;
    }

    showAddressEditModal(addressId): void{
        this.editAddressId = addressId;
        this.displayAddressEditModal = true;
    }

    showAddressCreateModal(){
        this.displayAddressCreateModal = true;
    }

    handleModalClose(value): void{
        this.displayEmployeeEditModal = value;
        this.displayPersonalDataEditModal = value;
        this.displayAddressEditModal = value;
        this.displayAddressCreateModal = value;
    }

    handleEmployeeUpdate(value): void{
        this.employee = value;
        this.displayEmployeeEditModal = false;
    }

    handlePersonalDataUpdate(value): void{
        this.personalData = value;
        this.displayPersonalDataEditModal = false;
    }

    handleAddressUpdate(): void{
        this.isLoading = true;
        this.getAddresses();
        this.displayPersonalDataEditModal = false;
        this.displayAddressCreateModal = false;
    }
}
