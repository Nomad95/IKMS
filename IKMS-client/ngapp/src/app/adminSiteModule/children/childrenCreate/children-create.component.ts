import {Component, OnInit, ViewChild} from '@angular/core';
import { EmployeeAdminService } from "../../services/employee-admin.service";
import { AddressAdminService } from "../../services/address.service";
import { PersonalDataAdminService } from "../../services/personal-data.service";
import { ActivatedRoute } from "@angular/router";
import {PersonalData} from "../../model/personalData/personal-data";
import {Address} from "../../model/address/address";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenAdminService} from "../../services/children-admin.service";
import {Child} from "../../menu/model/children/child";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {DateUtils} from "../../../commons/util/date-utils";
import {ParentAdminService} from "../../services/parent-admin.service";
import {Utils} from "../../../commons/util/utils";
import {CommonMessages} from "../../../commons/util/common-messages";

@Component({
  selector: 'child-create',
  templateUrl: './children-create.component.html',
  providers: [ChildrenAdminService, ParentAdminService, PersonalDataAdminService, EnumProvider]
})
export class ChildrenCreateComponent implements OnInit{
    constructor(
        private childrenAdminService: ChildrenAdminService,
        private parentAdminService: ParentAdminService,
        private personalDataAdminService: PersonalDataAdminService,
        private route: ActivatedRoute,
        private enumProvider: EnumProvider){}
    
    @ViewChild('personalDataForm') form;
    
    private child: Child;
    private personalData: PersonalData;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isCreating: boolean = false;
    private items: MenuItem[];
    private disabilityLevels = EnumProvider.DISABILITY_LEVELS;
    private genders = EnumProvider.GENDERS;
    private parents = [];
    
    ngOnInit(){
        this.items = BreadMaker.makeBreadcrumbs("Dzieci","Lista dzieci","Dodaj");
        this.disabilityLevels = this.enumProvider.translateToDropdown(this.disabilityLevels);
        this.genders = this.enumProvider.translateToDropdown(this.genders);
    
        this.getParentList();
    }
    
    clearForm(){
        this.child = new Child();
        this.personalData = new PersonalData();
    
        this.child.disabilityLevel = (<any>this.disabilityLevels[0]).value;
        this.personalData.gender = (<any>this.genders[0]).value;
        this.child.parent.id = (<any>this.parents[0]).value;
    }
    
    getParentList(){
        this.parentAdminService.getAllMinimal()
            .subscribe( data => {
            this.parents = Utils.minimalToDropdown(data);
            this.clearForm();
            this.isLoading = false;
        }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            });
    }
    
    onDateSelected(event){
        this.personalData.dateOfBirth = DateUtils.formatDate(event);
    }
    
    saveData(child, personalData){
        this.isCreating = true;
        this.personalDataAdminService.createPersonalData(personalData)
            .subscribe( data => {
                personalData = data;
                child.personalData.id = data.id;
                this.childrenAdminService.createChild(child)
                    .subscribe( data => {
                        this.msgs = CommonMessages.childCreatingSuccess(personalData.name + ' ' + personalData.surname);
                        this.isCreating = false;
                        this.form.reset();
                        this.clearForm();
                    });
            }, err => {
                this.msgs = CommonMessages.childCreatingError();
                this.isCreating = false;
            });
    }
}
