import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { EmployeeAdminService } from "../../services/employee-admin.service";
import { Employee } from "../../menu/model/employee/employee";
import { EnumProvider } from "../../../commons/util/enum-provider";
import {Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";

@Component({
  selector: 'employee-edit',
  templateUrl: './employee-edit.component.html',
  providers: [EmployeeAdminService, EnumProvider]
})
export class EmployeeEditComponent implements OnInit{
    constructor(
        private employeeAdminService: EmployeeAdminService,
        private enumProvider: EnumProvider){}
        
    @Input() private employeeId: number;
    @Input() private isVisible: boolean = false;
    
    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    private roles = EnumProvider.EMPLOYEE_ROLES;
    private employee: Employee = new Employee();
    private msgs: Message[] = [];
    
    ngOnInit(){
        this.employeeAdminService.getEmployee(this.employeeId)
            .subscribe( data => {
                console.log(data);
                this.employee = data;
                this.msgs = [];
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
       this.roles = this.enumProvider.translateToDropdown(this.roles);
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    saveData(employee){
        this.employeeAdminService.updateEmployee(employee)
        .subscribe( data => {
            this.eventSave.emit(data);
            this.isVisible = false;
            this.msgs = [];
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
  
}
