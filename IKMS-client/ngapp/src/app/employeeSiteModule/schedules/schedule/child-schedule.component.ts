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
import {ChildrenService} from "../../../sharedModule/services/children.service";

@Component({
  selector: 'child-schedule',
  templateUrl: './child-schedule.component.html',
  providers: []
})
export class ChildScheduleComponent implements OnInit{
    constructor(
        private scheduleService: ScheduleService,
        private childrenService: ChildrenService){}
  
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
    private children = [];
    private selectedChildId = -1;
    
    private displayActivityCreateModal = false;
    
    ngOnInit() {
        this.isLoading = true;
        this.getChildren();
        this.items = BreadMaker.makeBreadcrumbs("Plany","Plan dziecka");
    }
    
    getChildren(){
        this.childrenService.getChildrenMinimal()
            .subscribe( data => {
                this.children = Utils.minimalToDropdown(data);
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            });
    }
    
    onChildSelected(event){
        this.selectedChildId = event.value;
        this.scheduleService.getActivitiesFor('child', event.value).subscribe( data => {
            this.events = data;
            this.isLoading = false;
        });
    }
    
   
    handleModalClose(event){
        this.displayActivityCreateModal = false;
    }
    
}
