import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { AddressService } from "../../../sharedModule/services/address.service";
import { Address } from "../../model/address/address";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {Message} from "primeng/primeng";
import {CommonMessages} from "../../../commons/util/common-messages";

@Component({
  selector: 'address-create',
  templateUrl: './address-create.component.html',
  providers: [EnumProvider]
})
export class AddressCreateComponent implements OnInit{
    constructor(
        private addressService: AddressService,
        private enumProvider: EnumProvider){}

    @Input() private isVisible: boolean = false;
    @Input() private personalDataId: number = -1;

    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();

    private address: Address = new Address();
    private addressTypes = EnumProvider.ADDRESS_TYPES;
    private msgs: Message[] = [];

    ngOnInit(){
        this.addressTypes = this.enumProvider.translateToDropdown(this.addressTypes);
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }

    saveData(address){
        address.personalData = {id: this.personalDataId};
        this.addressService.createAddress(address)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
            this.address = new Address();
            this.msgs = CommonMessages.addressCreatingSuccess();
        }, err => this.msgs = CommonMessages.addressCreatingError());
    }

}
