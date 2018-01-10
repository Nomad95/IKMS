import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ConfirmationService, MenuItem, Message, TreeNode} from "primeng/primeng";
import {EmployeeService} from "../sharedModule/services/employee.service";
import {ChildrenService} from "../sharedModule/services/children.service";
import {ParentService} from "../sharedModule/services/parent.service";
import {ScheduleService} from "../sharedModule/services/schedule.service";
import {ScheduleActivity} from "../employeeSiteModule/model/schedule/schedule-activity";
import {Utils} from "../commons/util/utils";
import {ScheduleActivityDiaryDetail} from "../adminSiteModule/model/schedule/schedule-activity-diary-detail";
import {GroupService} from "../sharedModule/services/group.service";
import {BreadMaker} from "../commons/util/bread-maker";
import {Child} from "../employeeSiteModule/model/children/child";

@Component({
  selector: 'didactic-materials',
  templateUrl: './diary-activity-detail.component.html',
  providers: [ConfirmationService]
})
export class DiaryActivityDetailComponent implements OnInit{
    constructor(
        private confirmationService: ConfirmationService,
        private employeeService: EmployeeService,
        private groupSevice: GroupService,
        private parentService: ParentService,
        private childrenService: ChildrenService,
        private scheduleService: ScheduleService,
        private router: Router,
        private route: ActivatedRoute){}
    
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];
    private activityId: number;
    private noData = false;
    private scheduleActivityDetails: ScheduleActivityDiaryDetail;
    private selectedChild: Child;
    private recipientUsername = '';
    private displayMessage = false;
    private displayNotification = false;
    private displayRegistry = false;
    
    ngOnInit(){
        this.activityId = this.route.snapshot.params['activityId'];
        this.getActivity();
        this.isLoading = true;
        this.items = BreadMaker.makeBreadcrumbs("Dzienniki","Obecności");
    }
    
    getActivity(){
        this.scheduleService.getActivityDiaryDetails(this.activityId)
            .subscribe(data => {
                this.scheduleActivityDetails = data;
                console.log(data);
                this.isLoading = false;
            })
    }
    
    showMessageBox(username){
        this.recipientUsername = username;
        this.displayMessage = true;
    }
    
    showNotificationBox(username){
        this.recipientUsername = username;
        this.displayNotification = true;
    }
    
    showRegistry(){
        this.displayRegistry = true;
    }
    
    handleModalClose($event){
        this.displayRegistry = false;
    }
    
    handleOnModalSuccess($event){
        this.displayRegistry = false;
    }
}
