import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IConfig} from "./config/IConfig";
import {ScheduleConfigFactory} from "./config/scheduleConfigFactory";
import {ScheduleActivity} from "../adminSiteModule/model/schedule/schedule-activity";
import {DateUtils} from "../commons/util/date-utils";
import {ConfirmationService, Message} from "primeng/primeng";
import {CommonMessages} from "../commons/util/common-messages";
import {ScheduleService} from "../sharedModule/services/schedule.service";

@Component({
    selector: 'schedule-component',
    templateUrl: './schedule.component.html',
    providers: [ConfirmationService]
})
export class ScheduleComponent implements OnInit{
    constructor(
            private confirmationService: ConfirmationService,
            private scheduleService: ScheduleService
    ){}

    @Input() private scheduleType = undefined;
    @Input() private events: ScheduleActivity[] = [];
    @Input() private editMode: boolean = false;
    @Input() private deleteMode: boolean = false;
    
    @Output() private eventActivityUpdate = new EventEmitter();
    
    private configuration: IConfig;
    private isInEditMode: boolean = false;
   
    private displayActivityDetailModal = false;
    private displayActivityEditModal = false;
    private selectedEvent: ScheduleActivity = null;
    private msgs: Message[] = [];
    
    ngOnInit(){
        this.configuration = ScheduleConfigFactory.getConfig(this.scheduleType);
    }
    
    handleEventClick(event) {
        if (this.deleteMode) {
            this.deleteActivity(event.calEvent);
            return;
        }
        
        this.parseDate(event.calEvent);
    
        if (this.editMode) {
            this.displayActivityEditModal = true;
        } else {
            this.displayActivityDetailModal = true;
        }
    }
    
    onEventDrop(event){
        this.parseDate(event.event);
        this.eventActivityUpdate.emit(this.selectedEvent);
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
    
    deleteActivity(event){
        console.log(event);
        if (!event.id) {
            console.log("found!");
            for (let i = this.events.length - 1; i > 0; i--) {
                if (ScheduleActivity.equals(this.events[i],event)) {
                    console.log("SPLICE!");
                    this.events.splice(i, 1);
                    break;
                }
            }
        } else {
            this.deletionDialog(event.id);
        }
    }
    
    deletionDialog(activityId){
        this.confirmationService.confirm({
            message: 'Czy napewno chcesz usunąć te zajęcia?',
            header: 'Potwierdzenie usunięcia',
            accept: () => {
                this.scheduleService.deleteActivity(activityId)
                    .subscribe( data =>{
                        for (let i = this.events.length - 1; i > 0; i--) {
                            if (this.events[i].id == activityId) {
                                this.events.splice(i, 1);
                                break;
                            }
                        }
                    }, err => this.msgs = CommonMessages.activityDeletingError());
                },
                reject: () => {}
        });
    }
    
    changeEditMode(){
        this.editMode = !this.editMode;
    }
    
    changeView(schedule, viewName){
        schedule.changeView(viewName);
    }
    
    today(schedule){
        schedule.today();
    }
    
    left(schedule){
        schedule.prev();
    }
    
    right(schedule){
        schedule.next();
    }
    
    parseDate(activity){
        this.selectedEvent = activity;
        this.selectedEvent.start = DateUtils.objectToDate(this.selectedEvent.start);
        this.selectedEvent.start = DateUtils.substractSeconds(this.selectedEvent.start);
        this.selectedEvent.end = DateUtils.objectToDate(this.selectedEvent.end);
        this.selectedEvent.end = DateUtils.substractSeconds(this.selectedEvent.end);
    }
}
