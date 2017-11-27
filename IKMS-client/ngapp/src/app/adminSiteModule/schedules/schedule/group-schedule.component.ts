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
    private editMode: boolean = false;
    private deleteMode: boolean = false;
    private msgs: Message[] = [];
    private isLoading: boolean = false;
    private isUpdating: boolean = false;
    private schemaHasErrors: boolean = false;
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
    
    saveAndExitEdit(){
        this.isUpdating = true;
        for (let i = 0; i < this.events.length; i++){
            this.events[i].start = DateUtils.ISODateToServerLocalDateTime(this.events[i].start);
            this.events[i].end = DateUtils.ISODateToServerLocalDateTime(this.events[i].end);
        }
        
        this.events = Utils.deleteActivitiesUnwantedFields(this.events);
        
        this.scheduleService.createActivities(this.events)
            .subscribe( data => {
                if(this.selectedGroupId != -1){
                    this.scheduleService.getActivitiesFor('group', this.selectedGroupId).subscribe( data => {
                        this.events = data;
                        this.isLoading = false;
                    });
                }
                this.editMode = false;
                this.isUpdating = false;
                this.msgs = CommonMessages.scheduleCreatingSuccess();
            }, err => {
                this.isUpdating = false;
                this.msgs = CommonMessages.scheduleCreatingError()
            });
    }
    
    cancel(){
        this.events = JSON.parse(sessionStorage.getItem('cachedEvents'));
        this.scheduleService.createActivities(this.events)
            .subscribe( data => {
                if(this.selectedGroupId != -1){
                    this.scheduleService.getActivitiesFor('group', this.selectedGroupId).subscribe( data => {
                        this.events = data;
                        this.isLoading = false;
                    });
                }
            }, err => {
                this.msgs = CommonMessages.scheduleCreatingError()
            });
        this.editMode = false;
        this.deleteMode = false;
    }
    
    onGroupSelected(event){
        this.selectedGroupId = event.value;
        this.scheduleService.getActivitiesFor('group', event.value).subscribe( data => {
            this.events = data;
            this.isLoading = false;
        });
    }
    
    /**
     * Searches for time change and replaces with new activity.
     * Only needed with newly created activities.
     */
    onActivityUpdate(activity){
        if(!activity.id) {
            for (let i = this.events.length - 1; i > 0; i--) {
                if (this.events[i].title === activity.title) {
                    activity.start = activity.start + ':00';
                    activity.end = activity.end + ':00';
                    this.events[i] = activity;
                    break;
                }
            }
        }
    }
    
    onActivityValidationCheck(activity){
        for (let i = this.events.length - 1; i > 0; i--) {
            if (ScheduleActivity.equals(this.events[i], activity)) {
                this.events[i] = activity;
                
                if(activity.currentErrors.length > 0 || activity.errors.length > 0 ){
                    this.schemaHasErrors = true;
                }
                break;
            }
        }
    }
    
    resetChanges(){
        this.events = [];
        this.events = JSON.parse(sessionStorage.getItem('cachedEvents'));
    }
    
    showCreateModal() {
        this.displayActivityCreateModal = true;
    }
    
    enterEditMode() {
        sessionStorage.setItem('cachedEvents',JSON.stringify(this.events));
        this.editMode = true;
    }
    
    enterDeleteMode(){
        this.deleteMode = true;
    }
    
    exitDeleteMode(){
        this.deleteMode = false;
    }
    
    handleModalClose(event){
        this.displayActivityCreateModal = false;
    }
    
    handleActivityCreation(event){
        this.events.push(event);
        this.displayActivityCreateModal = false;
    }
}
