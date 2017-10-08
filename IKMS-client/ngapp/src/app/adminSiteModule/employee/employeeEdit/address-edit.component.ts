import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import { AddressAdminService } from "../../services/address.service";
import { Address } from "../../menu/model/address/address";

@Component({
  selector: 'address-edit',
  templateUrl: './address-edit.component.html',
  providers: [AddressAdminService]
})
export class AddressEditComponent implements OnInit, OnChanges{
    constructor(
        private addressAdminService: AddressAdminService){}
        
    @Input() private addressId: number;
    @Input() private isVisible: boolean = false;
    
    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    private address: Address = new Address();
    
    ngOnInit(){
        this.getAddress();
    }
    
    ngOnChanges(changes: SimpleChanges): void {
        this.getAddress();
    }
    
    getAddress(){
        if(this.addressId != -1) {
            this.addressAdminService.getAddress(this.addressId)
            .subscribe(data => {
                console.log(data);
                this.address = data;
            });
        }
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    saveData(address){
        this.addressAdminService.updateAddress(address)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
        });
    }
    
}