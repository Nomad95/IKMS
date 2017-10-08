import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { EmployeeAdminService } from "../../services/employee-admin.service";
import { Employee } from "../../menu/model/employee/employee";
import { EnumProvider } from "../../../commons/util/enum-provider";

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
    
    ngOnInit(){
        this.employeeAdminService.getEmployee(this.employeeId)
            .subscribe( data => {
                console.log(data);
                this.employee = data;
            });
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
        });
    }
  
}
