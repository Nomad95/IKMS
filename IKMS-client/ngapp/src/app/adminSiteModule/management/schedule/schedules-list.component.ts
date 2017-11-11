import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {GroupService} from "../../../sharedModule/services/group.service";

@Component({
  selector: 'schedule-list',
  templateUrl: './schedules-list.component.html',
  providers: []
})
export class SchedulesListComponent implements OnInit{
    constructor(
        private groupService: GroupService,
        private childrenService: ChildrenService,
        private router: Router,
        private route: ActivatedRoute){}
  
        //TODO: tylko przyklad
    private events: any[] = [];
    private scheduleType = 'WEEK_ACTIVITIES';
    
    ngOnInit(){
        
        this.events = [
            {
                title: 'Event',
                start: '2017-12-12T11:00:00',
                end: '2017-12-12T12:00:00',
                editable: true
            }
        ]
    }
}
