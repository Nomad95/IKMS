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
import {GroupAdminService} from "../../services/group.service";
import {Group} from "../../model/group/group";

@Component({
  selector: 'group-create',
  templateUrl: './group-create.component.html',
  providers: [ChildrenAdminService, EmployeeAdminService, GroupAdminService, EnumProvider]
})
export class GroupCreateComponent implements OnInit{
    constructor(
        private employeeAdminService: EmployeeAdminService,
        private groupService: GroupAdminService,
        private route: ActivatedRoute,
        private enumProvider: EnumProvider){}
    
    @ViewChild('groupForm') form;
    
    private group: Group;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isCreating: boolean = false;
    private items: MenuItem[];
    private yesNoChoice = EnumProvider.YES_NO;
    private employees = [];
    
    ngOnInit(){
        this. clearForm();
        this.items = BreadMaker.makeBreadcrumbs("Zarządzanie","Grupy","Dodaj");
        this.yesNoChoice = this.enumProvider.translateToDropdown(this.yesNoChoice);
        this.group.active = this.yesNoChoice[0];
    
        this.getEmployeeList();
    }
    
    clearForm(){
        this.group = new Group();
        this.form.reset();
    }
    
    getEmployeeList(){
        this.employeeAdminService.getEmployeesMinimal()
            .subscribe( data => {
            this.employees = Utils.minimalToDropdown(data);
            this.clearForm();
            this.isLoading = false;
        }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            });
    }
    
    saveData(group){
        this.isCreating = true;
        this.group.createdDate = DateUtils.formatDate(new Date());
        this.groupService.createGroup(group)
            .subscribe( data => {
                group = data;
                this.isCreating = false;
                this.msgs = CommonMessages.groupCreatingSuccess(data.name);
                this.clearForm();
            }, err => {
                this.msgs = CommonMessages.groupCreatingError();
                this.isCreating = false;
            });
    }
}
