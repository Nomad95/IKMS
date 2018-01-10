import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MenuItem, Message} from "primeng/primeng";
import {BreadMaker} from "../../commons/util/bread-maker";
import {ChildrenService} from "../../sharedModule/services/children.service";
import {AddressService} from "../../sharedModule/services/address.service";
import {PersonalDataService} from "../../sharedModule/services/personal-data.service";
import {ScheduleService} from "../../sharedModule/services/schedule.service";
import {ScheduleActivity} from "../model/schedule/schedule-activity";
import {IConfig} from "../../scheduleModule/config/iConfig";
import {ScheduleConfigFactory} from "../../scheduleModule/config/scheduleConfigFactory";

@Component({
  selector: 'main-diary-screen',
  templateUrl: './main-diary-screen.component.html',
  providers: []
})
export class MainDiaryScreen implements OnInit{
    constructor(
        private childrenService: ChildrenService,
        private addressService: AddressService,
        private personalDataService: PersonalDataService,
        private scheduleService: ScheduleService,
        private route: ActivatedRoute,
        private router: Router){}
    
    private events: ScheduleActivity[] = [];
    private oldEvents: ScheduleActivity[] = [];
    private scheduleType = 'DIARY_TODAY';
    private configuration: IConfig;
    private msgs: Message[] = [];
    private isLoading: boolean = false;
    private editMode = false;
    
    private items: MenuItem[];
    
    ngOnInit(){
        this.isLoading = true;
        this.getActivitiesForToday();
        this.configuration = ScheduleConfigFactory.getConfig(this.scheduleType);
        this.items = BreadMaker.makeBreadcrumbs("Dzienniki");
    }
    
    getActivitiesForToday(){
        this.scheduleService.getActivities().subscribe(data => {
            this.events = data;
            this.isLoading = false;
        });
    }
    
    handleEventClick(event){
        console.log("ID: " + event.calEvent.id);
        this.router.navigate(['/employee/diary/activity', event.calEvent.id]);
    }
}
