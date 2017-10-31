import {Component, OnInit} from "@angular/core";
import {ParentService} from "../../../sharedModule/services/parent.service";
import {AddressService} from "../../../sharedModule/services/address.service";
import {PersonalDataService} from "../../../sharedModule/services/personal-data.service";
import {ActivatedRoute} from "@angular/router";
import {PersonalData} from "../../model/personalData/personal-data";
import {Address} from "../../model/address/address";
import {MenuItem, Message} from "primeng/primeng";
import {Parent} from "../../menu/model/parent/parent";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";

@Component({
  selector: 'parent-detail',
  templateUrl: './parent-detail.component.html',
  providers: []
})
export class ParentDetailComponent implements OnInit {


  constructor(  private parentService: ParentService,
                private addressService: AddressService,
                private personalDataService: PersonalDataService,
                private route: ActivatedRoute) {}

  private parentId: number;
  private personalDataId: number;
  private parent: Parent;
  private personalData: PersonalData;
  private addresses: Address[];
  private editAddressId = -1;
  private msgs: Message[] = [];
  private isLoading: boolean = true;
  private items: MenuItem[];

  private displayParentEditModal = false;
  private displayPersonalDataEditModal = false;
  private displayAddressEditModal = false;

  private displayAddressCreateModal = false;

  ngOnInit(): void {
    this.parentId = this.route.snapshot.params['id'];
    this.personalDataId = this.route.snapshot.queryParams['personalDataId'] || -1;
    this.items = BreadMaker.makeBreadcrumbs("Rodzice","Lista rodziców","Podgląd");

    this.getParent();
    this.getPersonalData();
    this.getAddresses();
  }

  getParent(){
    this.isLoading = true;
    this.parentService.getParent(this.parentId)
      .subscribe( data => {
        this.parent = data;
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

  showParentEditModal(): void{
    this.displayParentEditModal = true;
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
    this.displayParentEditModal = value;
    this.displayPersonalDataEditModal = value;
    this.displayAddressEditModal = value;
    this.displayAddressCreateModal = value;
  }

  handleParentUpdate(value): void{
    this.parent = value;
    this.displayParentEditModal = false;
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
