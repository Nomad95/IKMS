import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import { AddressEmployeeService} from "../services/address.service";
import { Address } from "../model/address/address";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../commons/util/error-handler";

@Component({
  selector: 'address-edit',
  templateUrl: './address-edit.component.html',
  providers: [AddressEmployeeService]
})
export class AddressEditComponent implements OnInit, OnChanges{
    constructor(
        private addressEmployeeService: AddressEmployeeService){}
        
    @Input() private addressId: number;
    @Input() private isVisible: boolean = false;
    
    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    private address: Address = new Address();
    private msgs: Message[] = [];
    
    ngOnInit(){
        this.getAddress();
    }
    
    ngOnChanges(changes: SimpleChanges): void {
        this.getAddress();
    }
    
    getAddress(){
        if(this.addressId != -1) {
            this.addressEmployeeService.getAddress(this.addressId)
            .subscribe(data => {
                this.address = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
        }
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    saveData(address){
        this.addressEmployeeService.updateAddress(address)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
            this.msgs = [];
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
}