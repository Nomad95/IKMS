import {Component, Input, OnInit} from '@angular/core';
import {IConfig} from "./config/iConfig";
import {ScheduleConfigFactory} from "./config/scheduleConfigFactory";

@Component({
  selector: 'schedule-component',
  templateUrl: './schedule.component.html'
})
export class ScheduleComponent implements OnInit{
    
    @Input() private scheduleType = undefined;
    @Input() private events: any[] = [];
    private configuration: IConfig;
    private isInEditMode: boolean = false;
    
    ngOnInit(){
        this.configuration = ScheduleConfigFactory.getConfig(this.scheduleType);
        
        this.events = [
            {
                title: 'Event',
                start: '2017-12-12T11:00:00',
                end: '2017-12-12T13:00:00',
                editable: true
            }
        ]
    }
    
    changeEditMode(){
        this.isInEditMode = !this.isInEditMode;
    }
}
