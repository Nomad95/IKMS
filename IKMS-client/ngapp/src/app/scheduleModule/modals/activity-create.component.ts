import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Message, SelectItem} from "primeng/primeng";
import {ScheduleService} from "../../sharedModule/services/schedule.service";
import {EnumProvider} from "../../commons/util/enum-provider";
import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";
import {CommonMessages} from "../../commons/util/common-messages";
import {MinimalDto} from "../../adminSiteModule/model/minimal-dto";
import {EmployeeService} from "../../sharedModule/services/employee.service";
import {Utils} from "../../commons/util/utils";
import {ChildrenService} from "../../sharedModule/services/children.service";
import {GroupService} from "../../sharedModule/services/group.service";
import {ClassroomService} from "../../sharedModule/services/classroom.service";
import {DateUtils} from "../../commons/util/date-utils";

@Component({
  selector: 'activity-create',
  templateUrl: './activity-create.component.html',
  providers: [EnumProvider]
})
export class ActivityCreateComponent implements OnInit{
    constructor(
        private activityService: ScheduleService,
        private enumProvider: EnumProvider,
        private employeeService: EmployeeService,
        private childrenService: ChildrenService,
        private groupService: GroupService,
        private classroomService: ClassroomService){
        
        this.childrenList = [];
        this.employeeList = [];
    }
    
    @Input() private isVisible: boolean = false;
    @Input() private personalDataId: number = -1;

    @Output() eventClose = new EventEmitter();
    @Output() eventSave = new EventEmitter();
    
    @ViewChild('createForm') form;
    
    
    private activity: ScheduleActivity = new ScheduleActivity();
    private childrenList = [];
    private employeeList = [];
    private groupList = [];
    private classroomList = [];
    private msgs: Message[] = [];
    private defaultDate = new Date();

    ngOnInit(){
        this.getEmployees();
        this.getChildren();
        this.getGroups();
        this.getClassrooms();
        this.defaultDate.setMinutes(0);
    }
    
    getChildren(){
        this.childrenService.getChildrenMinimal()
            .subscribe( data => {
                this.childrenList = this.childrenList.concat(Utils.minimalToDropdownMinimal(data));
            });
    }
    
    getEmployees(){
        this.employeeService.getEmployeesMinimal()
            .subscribe( data => {
                this.employeeList = this.employeeList.concat(Utils.minimalToDropdownMinimal(data));
            });
    }
    
    getGroups(){
        this.groupService.getGroupsMinimal()
            .subscribe( data => {
                this.groupList = Utils.minimalToDropdownMinimal(data);
            });
    }
    
    getClassrooms(){
        this.classroomService.getClassroomMinimal()
            .subscribe( data => {
                this.classroomList = Utils.minimalToDropdownMinimal(data);
            });
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    onDateSelected(event, type){
         if (type == 'start') {
             this.activity.start = DateUtils.formatDateTime(event);
         } else if (type == 'end'){
             this.activity.end = DateUtils.formatDateTime(event);
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
    }

}
