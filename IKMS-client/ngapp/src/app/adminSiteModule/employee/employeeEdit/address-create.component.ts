import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { AddressAdminService } from "../../services/address.service";
import { Address } from "../../menu/model/address/address";
import {EnumProvider} from "../../../commons/util/enum-provider";

@Component({
  selector: 'address-create',
  templateUrl: './address-create.component.html',
  providers: [AddressAdminService, EnumProvider]
})
export class AddressCreateComponent implements OnInit{
    constructor(
        private addressAdminService: AddressAdminService,
        private enumProvider: EnumProvider){}
        
    @Input() private isVisible: boolean = false;
    @Input() private personalDataId: number = -1;
    
    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    private address: Address = new Address();
    private addressTypes = EnumProvider.ADDRESS_TYPES;
    
    ngOnInit(){
        this.addressTypes = this.enumProvider.translateToDropdown(this.addressTypes);
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    saveData(address){
        address.personalData = {id: this.personalDataId};
        this.addressAdminService.createAddress(address)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
            this.address = new Address();
        });
    }
    
}