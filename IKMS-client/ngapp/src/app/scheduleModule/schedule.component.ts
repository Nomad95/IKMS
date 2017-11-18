import {Component, Input, OnInit} from '@angular/core';
import {IConfig} from "./config/IConfig";
import {ScheduleConfigFactory} from "./config/scheduleConfigFactory";
import {ScheduleActivity} from "../adminSiteModule/model/schedule/schedule-activity";
import {DateUtils} from "../commons/util/date-utils";

@Component({
  selector: 'schedule-component',
  templateUrl: './schedule.component.html'
})
export class ScheduleComponent implements OnInit{

    @Input() private scheduleType = undefined;
    @Input() private events: any[] = [];
    @Input() private editMode: boolean = false;
    
    private configuration: IConfig;
    private isInEditMode: boolean = false;

   
    private displayActivityDetailModal = false;
    private displayActivityEditModal = false;
    private selectedEvent: ScheduleActivity = null;
    
    ngOnInit(){
        this.configuration = ScheduleConfigFactory.getConfig(this.scheduleType);
    }

    changeEditMode(){
        this.editMode = !this.editMode;
    }
    
    handleEventClick(e){
        this.selectedEvent = e.calEvent;
        this.selectedEvent.start = DateUtils.objectToDate(this.selectedEvent.start);
        this.selectedEvent.start = DateUtils.substractSeconds(this.selectedEvent.start);
        this.selectedEvent.end = DateUtils.objectToDate(this.selectedEvent.end);
        this.selectedEvent.end = DateUtils.substractSeconds(this.selectedEvent.end);
    
        if (this.editMode){
            this.displayActivityEditModal = true;
        } else {
            this.displayActivityDetailModal = true;
        }
    }
    
    handleDetailModalClose(){
        this.displayActivityDetailModal = false;
        this.displayActivityEditModal = false;
        this.selectedEvent = null;
    }
    
    handleEditModalClose(activity){
        this.displayActivityEditModal = false;
        for (let i = 0; i < this.events.length; i++) {
            if (this.events[i].id == activity.id) {
                this.events[i] = activity;
                this.selectedEvent = null;
                return;
            }
        }
        
    }
}
