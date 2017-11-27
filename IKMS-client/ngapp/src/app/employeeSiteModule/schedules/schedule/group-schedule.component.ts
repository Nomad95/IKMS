import { Component, OnInit } from '@angular/core';
import {ScheduleService} from "../../../sharedModule/services/schedule.service";
import {ScheduleActivity} from "../../model/schedule/schedule-activity";
import {DateUtils} from "../../../commons/util/date-utils";
import {CommonMessages} from "../../../commons/util/common-messages";
import {MenuItem, Message} from "primeng/primeng";
import {Utils} from "../../../commons/util/utils";
import {Observable} from "rxjs/Observable";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {EmployeeService} from "../../../sharedModule/services/employee.service";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {GroupService} from "../../../sharedModule/services/group.service";

@Component({
  selector: 'group-schedule',
  templateUrl: './group-schedule.component.html',
  providers: []
})
export class GroupScheduleComponent implements OnInit{
    constructor(
        private scheduleService: ScheduleService,
        private groupService: GroupService){}
  
    private events: ScheduleActivity[] = [];
    private oldEvents: ScheduleActivity[] = [];
    private scheduleType = 'WEEK_ACTIVITIES';
    private msgs: Message[] = [];
    private isLoading: boolean = false;
    private items: MenuItem[];
    private groups = [];
    private selectedGroupId = -1;
    
    private displayActivityCreateModal = false;
    
    ngOnInit() {
        this.isLoading = true;
        this.getGroups();
        this.items = BreadMaker.makeBreadcrumbs("Plany","Plan grupy");
    }
    
    getGroups(){
        this.groupService.getGroupsMinimal()
            .subscribe( data => {
                this.groups = Utils.minimalToDropdown(data);
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            });
    }
    
    onGroupSelected(event){
        this.selectedGroupId = event.value;
        this.scheduleService.getActivitiesFor('group', event.value).subscribe( data => {
            this.events = data;
            this.isLoading = false;
        });
    }
    
    handleModalClose(event){
        this.displayActivityCreateModal = false;
    }
    
}
