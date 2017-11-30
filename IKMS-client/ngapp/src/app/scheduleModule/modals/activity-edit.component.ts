import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Message} from "primeng/primeng";
import {EnumProvider} from "../../commons/util/enum-provider";
import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";
import {EmployeeService} from "../../sharedModule/services/employee.service";
import {Utils} from "../../commons/util/utils";
import {ChildrenService} from "../../sharedModule/services/children.service";
import {GroupService} from "../../sharedModule/services/group.service";
import {ClassroomService} from "../../sharedModule/services/classroom.service";
import {DateUtils} from "../../commons/util/date-utils";
import {ErrorHandler} from "../../commons/util/error-handler";

@Component({
  selector: 'activity-edit',
  templateUrl: './activity-edit.component.html',
  providers: [EnumProvider]
})
export class ActivityEditComponent implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private childrenService: ChildrenService,
        private enumProvider: EnumProvider,
        private groupService: GroupService,
        private classroomService: ClassroomService){
        
        this.childrenList = [];
        this.employeeList = [];
    }
    
    @Input() private isVisible: boolean = false;
    @Input() private activity:  ScheduleActivity = new ScheduleActivity();

    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    @ViewChild('editForm') form;
    
    private childrenList = [];
    private employeeList = [];
    private groupList = [];
    private classroomList = [];
    private msgs: Message[] = [];
    private defaultDate = new Date();
    private minDate = null;
    private maxDate = null;
    private pl = Utils.polishLocale;
    private classTypes = EnumProvider.CLASS_TYPES;
    
    ngOnInit(){
        this.getEmployees();
        this.getChildren();
        this.getGroups();
        this.getClassrooms();
        this.defaultDate.setMinutes(0);
        this.classTypes = this.enumProvider.translateToDropdown(this.classTypes);
    }
    
    onDateSelected(event, type){
         if (type == 'start') {
             this.activity.start = DateUtils.formatDateTime(event);
             this.minDate = DateUtils.toPrimeNgCalendarDate(event);
         } else if (type == 'end') {
             this.activity.end = DateUtils.formatDateTime(event);
             this.maxDate = DateUtils.toPrimeNgCalendarDate(event);
         }
    }
    
    saveData(activity){
        let activityCopy = {...activity};
        activityCopy.start = DateUtils.addSeconds(activityCopy.start);
        activityCopy.end = DateUtils.addSeconds(activityCopy.end);
        
        this.eventSave.emit(activityCopy);
        this.isVisible = false;
        this.activity = new ScheduleActivity();
        this.form.reset();
        this.maxDate = null;
        this.minDate = null;
    }
    
    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    getChildren(){
        this.childrenService.getChildrenMinimal()
        .subscribe( data => {
            this.childrenList = this.childrenList.concat(Utils.minimalToDropdownMinimal(data));
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getEmployees(){
        this.employeeService.getEmployeesMinimal()
        .subscribe( data => {
            this.employeeList = this.employeeList.concat(Utils.minimalToDropdownMinimal(data));
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getGroups(){
        this.groupService.getGroupsMinimal()
        .subscribe( data => {
            this.groupList = Utils.minimalToDropdownMinimal(data);
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
    
    getClassrooms(){
        this.classroomService.getClassroomMinimal()
        .subscribe( data => {
            this.classroomList = Utils.minimalToDropdownMinimal(data);
        }, err => this.msgs = ErrorHandler.handleGenericServerError(err));
    }
}
