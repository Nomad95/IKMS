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
import {PersonalDataService} from "../../../sharedModule/services/personal-data.service";

@Component({
  selector: 'employee-schedule',
  templateUrl: './employee-schedule.component.html',
  providers: []
})
export class EmployeeScheduleComponent implements OnInit{
    constructor(
        private scheduleService: ScheduleService,
        private personalDataService: PersonalDataService){}
  
    private events: ScheduleActivity[] = [];
    private oldEvents: ScheduleActivity[] = [];
    private scheduleType = 'WEEK_ACTIVITIES';
    private msgs: Message[] = [];
    private isLoading: boolean = false;
    private items: MenuItem[];
    private userName: string = '';
    
    private displayActivityCreateModal = false;
    
    ngOnInit() {
        this.getCurrentUserName();
        this.isLoading = true;
        this.getEmployeeActivities();
        this.items = BreadMaker.makeBreadcrumbs("Plany","Plan pracownika");
    }
    
    getCurrentUserName(){
        this.personalDataService.getCurrentUserName()
        .subscribe( data => {
            this.userName = data.value;
        }, err => ErrorHandler.handleGenericServerError(err));
    }
    
    getEmployeeActivities(){
        this.scheduleService.getActivitiesForLoggedEmployee()
            .subscribe( data => {
                this.events = data;
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            })
    }
    
    handleModalClose(event){
        this.displayActivityCreateModal = false;
    }
    
}
