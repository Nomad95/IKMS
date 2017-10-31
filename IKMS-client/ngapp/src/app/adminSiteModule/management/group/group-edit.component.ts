import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EnumProvider } from "../../../commons/util/enum-provider";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {GroupService} from "../../../sharedModule/services/group.service";
import {Group} from "../../model/group/group";
import {EmployeeService} from "../../../sharedModule/services/employee.service";
import {Utils} from "../../../commons/util/utils";

@Component({
  selector: 'group-edit',
  templateUrl: './group-edit.component.html',
  providers: [EnumProvider]
})
export class GroupEditComponent implements OnInit{
    constructor(
        private groupService: GroupService,
        private employeeService: EmployeeService,
        private enumProvider: EnumProvider){}
        
    @Input() private groupId: number;
    @Input() private isVisible: boolean = false;
    
    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    private group: Group = new Group();
    private employees: any[] = [];
    private msgs: Message[] = [];
    private yesNoChoice = EnumProvider.YES_NO;
    
    ngOnInit(){
        this.getGroup();
        this.getEmployees();
        this.yesNoChoice = this.enumProvider.translateToDropdown(this.yesNoChoice);
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    saveData(group){
        this.groupService.updateGroup(group)
            .subscribe( data => {
                this.eventSave.emit(data);
                this.isVisible = false;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getGroup(){
        this.groupService.getGroup(this.groupId)
        .subscribe( data => {
            this.group = data;
            this.msgs = [];
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getEmployees(){
        this.employeeService.getEmployeesMinimal()
            .subscribe( data => {
                this.employees = Utils.minimalToDropdownMinimal(data);
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
  
}
