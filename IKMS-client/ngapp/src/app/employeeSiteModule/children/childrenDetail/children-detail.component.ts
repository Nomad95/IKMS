import { Component, OnInit } from '@angular/core';
import { EmployeeEmployeeService } from "../../services/employee-employee.service";
import { AddressEmployeeService } from "../../services/address.service";
import { PersonalDataEmployeeService } from "../../services/personal-employee.service";
import { ActivatedRoute } from "@angular/router";
import {PersonalData} from "../../model/personalData/personal-data";
import {Address} from "../../model/address/address";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenEmployeeService} from "../../services/children-employee.service";
import {Child} from "../../model/children/child";

@Component({
  selector: 'child-detail',
  templateUrl: './children-detail.component.html',
  providers: [ChildrenEmployeeService, AddressEmployeeService, PersonalDataEmployeeService]
})
export class ChildrenDetailComponent implements OnInit{
    constructor(
        private childrenEmployeeService: ChildrenEmployeeService,
        private addressEmployeeService: AddressEmployeeService,
        private personalDataEmployeeService: PersonalDataEmployeeService,
        private route: ActivatedRoute){}
  
    private childId: number;
    private personalDataId: number;
    private child: Child;
    private personalData: PersonalData;
    private addresses: Address[];
    private editAddressId = -1;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];
    
    private displayChildrenEditModal = false;
    private displayPersonalDataEditModal = false;
    private displayAddressEditModal = false;
    
    private displayAddressCreateModal = false;
    
    ngOnInit(){
        this.childId = this.route.snapshot.params['id'];
        this.personalDataId = this.route.snapshot.queryParams['personalDataId'] || -1;
        this.items = BreadMaker.makeBreadcrumbs("Dzieci","Lista dzieci","Podgląd");
        
        this.getChild();
        this.getPersonalData();
        this.getAddresses();
    }
    
    getChild(){
        this.isLoading = true;
        this.childrenEmployeeService.getChild(this.childId)
            .subscribe( data => {
                this.child = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getPersonalData(){
        this.personalDataEmployeeService.getPersonalData(this.personalDataId)
            .subscribe( data => {
                this.personalData = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getAddresses(){
        this.addressEmployeeService.getAddressesByPersonalDataId(this.personalDataId)
            .subscribe( data => {
                this.addresses = data;
                this.msgs = [];
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            });
    }
    
    showChildrenEditModal(): void{
        this.displayChildrenEditModal = true;
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
        this.displayChildrenEditModal = value;
        this.displayPersonalDataEditModal = value;
        this.displayAddressEditModal = value;
        this.displayAddressCreateModal = value;
    }
    
    handleChildUpdate(value): void{
        this.child = value;
        this.displayChildrenEditModal = false;
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
    
    showParentAddress(): void{
        this.addressEmployeeService.getAddressesByParentId(this.child.parent.id)
            .subscribe( data => {
                console.log(data);
                this.addresses = data;
                this.msgs = [];
            }, err =>{
                this.msgs = ErrorHandler.handleGenericServerError(err);
            } );
    }
    
}
