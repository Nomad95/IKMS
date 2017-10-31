import { Component, OnInit } from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import { AddressService } from "../../../sharedModule/services/address.service";
import { PersonalDataService } from "../../../sharedModule/services/personal-data.service";
import { ActivatedRoute } from "@angular/router";
import {PersonalData} from "../../model/personalData/personal-data";
import {Address} from "../../model/address/address";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {Child} from "../../menu/model/children/child";

@Component({
  selector: 'child-detail',
  templateUrl: './children-detail.component.html',
  providers: []
})
export class ChildrenDetailComponent implements OnInit{
    constructor(
        private childrenService: ChildrenService,
        private addressService: AddressService,
        private personalDataService: PersonalDataService,
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
        this.childrenService.getChild(this.childId)
            .subscribe( data => {
                this.child = data;
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
        this.addressService.getAddressesByParentId(this.child.parent.id)
            .subscribe( data => {
                console.log(data);
                this.addresses = data;
                this.msgs = [];
            }, err =>{
                this.msgs = ErrorHandler.handleGenericServerError(err);
            } );
    }
    
}
