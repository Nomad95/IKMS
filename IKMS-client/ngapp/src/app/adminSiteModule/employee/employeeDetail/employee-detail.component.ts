import { Component, OnInit } from '@angular/core';
import { EmployeeAdminService } from "../../services/employee-admin.service";
import { AddressAdminService } from "../../services/address.service";
import { PersonalDataAdminService } from "../../services/personal-data.service";
import { ActivatedRoute } from "@angular/router";
import { Employee } from "../../menu/model/employee/employee";
import {PersonalData} from "../../menu/model/personalData/personal-data";
import {Address} from "../../menu/model/address/address";

@Component({
  selector: 'employee-detail',
  templateUrl: './employee-detail.component.html',
  providers: [EmployeeAdminService, AddressAdminService, PersonalDataAdminService]
})
export class EmployeeDetailComponent implements OnInit{
    constructor(
        private employeeAdminService: EmployeeAdminService,
        private addressAdminService: AddressAdminService,
        private personalDataAdminService: PersonalDataAdminService,
        private route: ActivatedRoute){}
  
    private employeeId: number;
    private personalDataId: number;
    private employee: Employee;
    private personalData: PersonalData;
    private addresses: Address[];
    private editAddressId = -1;
    
    private displayEmployeeEditModal = false;
    private displayPersonalDataEditModal = false;
    private displayAddressEditModal = false;
    private displayAddressCreateModal = false;
    
    ngOnInit(){
        this.employeeId = this.route.snapshot.params['id'];
        this.personalDataId = this.route.snapshot.queryParams['personalDataId'] || -1;
        
        this.getEmployee();
        this.getPersonalData();
        this.getAddresses();
    }
    
    getEmployee(){
        this.employeeAdminService.getEmployee(this.employeeId)
            .subscribe( data => {
                this.employee = data;
                console.log(data);
            }, err => console.log(err));
    }
    
    getPersonalData(){
        this.personalDataAdminService.getPersonalData(this.personalDataId)
            .subscribe( data => {
                this.personalData = data;
                console.log(data);
            }, err => console.log(err));
    }
    
    getAddresses(){
        this.addressAdminService.getAddressesByPersonalDataId(this.personalDataId)
            .subscribe( data => {
                this.addresses = data;
                console.log(data);
            }, err => console.log(err));
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
        this.getAddresses();
        this.displayPersonalDataEditModal = false;
        this.displayAddressCreateModal = false;
    }
}
