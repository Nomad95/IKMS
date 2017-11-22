import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Message} from "primeng/primeng";
import {EnumProvider} from "../../commons/util/enum-provider";
import {ScheduleActivity} from "../../adminSiteModule/model/schedule/schedule-activity";

@Component({
  selector: 'activity-details',
  templateUrl: './activity-details.component.html',
  providers: [EnumProvider]
})
export class ActivityDetailsComponent {
    constructor(){}
    
    @Input() private isVisible: boolean = false;
    @Input() private event: ScheduleActivity;

    @Output() eventClose = new EventEmitter();
    
    private msgs: Message[] = [];

    closeModal(){
        this.isVisible = false;
        this.eventClose.emit(false);
    }
    
    /**
     * displays date on screen
     */
    getDate(date){
        if (typeof date === "string"){
            return date;
        } else return date._i;
    }
    
    /**
     * displays element on screen
     */
    getElement(element){
        if (element == null){
            return '-';
        } else return element.value;
    }
}
