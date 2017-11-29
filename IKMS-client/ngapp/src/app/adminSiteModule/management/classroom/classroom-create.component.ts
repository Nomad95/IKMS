import {Component, OnInit, ViewChild} from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import {MenuItem, Message} from "primeng/primeng";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {EnumProvider} from "../../../commons/util/enum-provider";
import {CommonMessages} from "../../../commons/util/common-messages";
import {Classroom} from "../../model/classroom/classroom";
import {ClassroomService} from "../../../sharedModule/services/classroom.service";

@Component({
  selector: 'classroom-create',
  templateUrl: './classroom-create.component.html',
  providers: [EmployeeService,EnumProvider]
})
export class ClassroomCreateComponent implements OnInit{
    constructor(
        private classroomService: ClassroomService,
        private enumProvider: EnumProvider){}
    
    @ViewChild('classroomForm') form;
    
    private classroom: Classroom;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isCreating: boolean = false;
    private items: MenuItem[];
    private yesNoChoice = EnumProvider.YES_NO;
    
    ngOnInit(){
        this.items = BreadMaker.makeBreadcrumbs("Zarządzanie","Sale","Dodaj");
        this.yesNoChoice = this.enumProvider.translateToDropdown(this.yesNoChoice);
    
        this.clearForm();
        this.isLoading = false;
    }
    
    clearForm(){
        this.classroom = new Classroom();
        this.form.reset();
        this.classroom.available = (<any>this.yesNoChoice[0]).value;
    }
    
    saveData(classroom){
        this.isCreating = true;
        this.classroomService.createClassroom(classroom)
            .subscribe( data => {
                classroom = data;
                this.isCreating = false;
                this.msgs = CommonMessages.classroomCreatingSuccess(data.name);
                this.clearForm();
            }, err => {
                this.msgs = CommonMessages.classroomCreatingError();
                this.isCreating = false;
            });
    }
}
