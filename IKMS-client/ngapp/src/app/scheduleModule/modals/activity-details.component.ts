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
  selector: 'activity-details',
  templateUrl: './activity-details.component.html',
  providers: [EnumProvider]
})
export class ActivityDetailsComponent implements OnInit{
    constructor(
        private activityService: ScheduleService,
        private enumProvider: EnumProvider,
        private employeeService: EmployeeService,
        private childrenService: ChildrenService,
        private groupService: GroupService,
        private classroomService: ClassroomService){}
    
    @Input() private isVisible: boolean = false;
    @Input() private event: ScheduleActivity;

    @Output() eventClose = new EventEmitter();
    
    private msgs: Message[] = [];

    ngOnInit(){
    }

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }

    getDate(date){
        if (typeof date === "string"){
            return date;
        } else return date._i;
    }
    
    getElement(element){
        if (element == null){
            return '-';
        } else return element.value;
    }
}
