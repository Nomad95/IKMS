import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { RegistrationService } from "../../services/registration.service";
import { EnumProvider } from "../../../commons/util/enum-provider";
import { Address } from "../../model/address/address";
import { UtilMethods } from '../../../commons/util/util-methods.service';


@Component({
  selector: 'address-registration',
  templateUrl: './address-registration.component.html',
  providers: [RegistrationService]
})

export class AddressRegistrationComponent implements OnInit {

  constructor(private registrationService: RegistrationService, private utilMethods: UtilMethods, private enumProvider: EnumProvider) { }

  @Output() deleteAddressEmitter: EventEmitter<number> = new EventEmitter<number>();
  @Output() resetAddressEmitter: EventEmitter<number> = new EventEmitter<number>();
  @Output() isAddressValidEmitter: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() address: Address;
  @Input() id: number;
  private addressTypes = EnumProvider.ADDRESS_TYPES;

  ngOnInit() {
    this.addressTypes = this.enumProvider.translateToDropdown(this.addressTypes);
    this.address.addressType = this.addressTypes[0]['value'];
    this.addressIsValid();
  }

  deleteAddress() {
    this.deleteAddressEmitter.emit(this.id);
  }

  resetAddress() {
    this.resetAddressEmitter.emit(this.id);
  }

  addressIsValid() {
    this.isAddressValidEmitter.emit(this.address.streetNumber > 0);
  }

  firstLetterUpperCase(value: string) {
    return this.utilMethods.firstLetterUpperCase(value);
  }

}
