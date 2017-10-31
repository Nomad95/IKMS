import {Component, OnInit, ViewChild} from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import { ActivatedRoute } from "@angular/router";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {DateUtils} from "../../../commons/util/date-utils";
import {Utils} from "../../../commons/util/utils";
import {CommonMessages} from "../../../commons/util/common-messages";
import {GroupService} from "../../../sharedModule/services/group.service";
import {Group} from "../../model/group/group";

@Component({
  selector: 'group-create',
  templateUrl: './group-create.component.html',
  providers: [EmployeeService,EnumProvider]
})
export class GroupCreateComponent implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private groupService: GroupService,
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
        this.employeeService.getEmployeesMinimal()
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
