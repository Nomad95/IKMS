import { Component, OnInit } from '@angular/core';
import {ScheduleService} from "../../../sharedModule/services/schedule.service";
import {ScheduleActivity} from "../../model/schedule/schedule-activity";
import {DateUtils} from "../../../commons/util/date-utils";
import {CommonMessages} from "../../../commons/util/common-messages";
import {MenuItem, Message} from "primeng/primeng";
import {Utils} from "../../../commons/util/utils";
import {Observable} from "rxjs/Observable";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {PersonalDataService} from "../../../sharedModule/services/personal-data.service";
import {ErrorHandler} from "../../../commons/util/error-handler";

@Component({
  selector: 'collective-schedule',
  templateUrl: './collective-schedule.component.html',
  providers: []
})
export class CollectiveScheduleComponent implements OnInit{
    constructor(
        private scheduleService: ScheduleService){}
  
    private events: ScheduleActivity[] = [];
    private oldEvents: ScheduleActivity[] = [];
    private scheduleType = 'WEEK_ACTIVITIES';
    private msgs: Message[] = [];
    private isLoading: boolean = false;

    private items: MenuItem[];
    
    private displayActivityCreateModal = false;
    
    ngOnInit() {
        this.getActivities();
        this.items = BreadMaker.makeBreadcrumbs("Plany","Plan zbiorczy");
        this.isLoading = true;
    }
    
    getActivities(){
        this.scheduleService.getActivities().subscribe( data => {
            this.events = data;
            this.isLoading = false;
        });
    }
    
    handleModalClose(event){
        this.displayActivityCreateModal = false;
    }

}
