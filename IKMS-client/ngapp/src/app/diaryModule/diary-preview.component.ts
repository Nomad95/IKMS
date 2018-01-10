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
import {DiaryPreviewDto} from "../employeeSiteModule/model/diary/diaryPreviewDto";
import {DateUtils} from "../commons/util/date-utils";

@Component({
  selector: 'didactic-materials',
  templateUrl: './diary-preview.component.html',
  providers: [ConfirmationService]
})
export class DiaryPreviewComponent implements OnInit{
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
    private children = [{label: 'New York', code: 'NY'},
        {label: 'Rome', code: 'RM'},
        {label: 'London', code: 'LDN'},
        {label: 'Istanbul', code: 'IST'},
        {label: 'Paris', code: 'PRS'}];
    private registries = [];
    private diaryPreviewDto: DiaryPreviewDto = new DiaryPreviewDto();
    private activityId: number;
    private noData = false;
    private scheduleActivityDetails: ScheduleActivityDiaryDetail;
    private selectedChild: Child;
    private recipientUsername = '';
    private displayMessage = false;
    private displayNotification = false;
    private displayRegistry = false;
    private defaultDate = new Date();
    private pl = Utils.polishLocale;
    private minDate = null;
    private maxDate = null;
    
    ngOnInit(){
        this.activityId = this.route.snapshot.params['activityId'];
        this.getActivity();
        this.isLoading = false;//todo:
        this.items = BreadMaker.makeBreadcrumbs("Dzienniki","Podgląd obecności");
        this.defaultDate.setMinutes(0);
    }
    
    getActivity(){
        this.scheduleService.getActivityDiaryDetails(this.activityId)
            .subscribe(data => {
                this.scheduleActivityDetails = data;
                console.log(data);
                this.isLoading = false;
            })
    }
    
    onChildSelected(){
        //todo
    }
    
    onDateSelected(event, type){
        if (type == 'start') {
            this.minDate = DateUtils.toPrimeNgCalendarDate(event);
        } else if (type == 'end') {
            this.maxDate = DateUtils.toPrimeNgCalendarDate(event);
        }
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
