import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {GroupService} from "../../../sharedModule/services/group.service";
import {ScheduleService} from "../../../sharedModule/services/schedule.service";
import {ScheduleActivity} from "../../model/schedule/schedule-activity";
import {DateUtils} from "../../../commons/util/date-utils";
import {CommonMessages} from "../../../commons/util/common-messages";
import {Message} from "primeng/primeng";
import {Utils} from "../../../commons/util/utils";

@Component({
  selector: 'collective-schedule',
  templateUrl: './collective-schedule.component.html',
  providers: []
})
export class CollectiveScheduleComponent implements OnInit{
    constructor(
        private groupService: GroupService,
        private childrenService: ChildrenService,
        private scheduleService: ScheduleService,
        private router: Router,
        private route: ActivatedRoute){}
  
    private events: ScheduleActivity[] = [];
    private oldEvents: ScheduleActivity[] = [];
    private scheduleType = 'WEEK_ACTIVITIES';
    private editMode: boolean = false;
    private msgs: Message[] = [];
    private isLoading: boolean = false;
    private isUpdating: boolean = false;
    
    private displayActivityCreateModal = false;
    
    ngOnInit() {
        this.isLoading = true;
        this.scheduleService.getActivities().subscribe( data => {
            this.events = data;
            this.isLoading = false;
        });
    }
    
    saveAndExitEdit(){
        this.isUpdating = true;
        for (let i = 0; i < this.events.length; i++){
            this.events[i].start = DateUtils.ISODateToServerLocalDateTime(this.events[i].start);
            this.events[i].end = DateUtils.ISODateToServerLocalDateTime(this.events[i].end);
            this.events = Utils.deleteActivityUnwantedFields(this.events);
        }
        this.scheduleService.createActivities(this.events)
            .subscribe( data => {
                this.events = data;
                this.editMode = false;
                this.isUpdating = false;
                this.msgs = CommonMessages.scheduleCreatingSuccess();
            }, err => {
                this.isUpdating = false;
                this.msgs = CommonMessages.scheduleCreatingError()
            });
    }
    
    changeEditMode() {
        sessionStorage.setItem('cachedEvents',JSON.stringify(this.events));
        this.editMode = !this.editMode;
    }
    
    resetChanges(){
        this.events = [];
        this.events = JSON.parse(sessionStorage.getItem('cachedEvents'));
    }
    
    showCreateModal() {
        this.displayActivityCreateModal = true;
    }
    
    handleModalClose(event){
        this.displayActivityCreateModal = false;
    }
    
    handleAddressUpdate(event){
        this.events.push(event);
        this.displayActivityCreateModal = false;
    }
    
    cancel(){
        this.events = JSON.parse(sessionStorage.getItem('cachedEvents'));
        this.editMode = false;
    }
}
