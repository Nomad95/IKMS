import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenAdminService} from "../../services/children-admin.service";
import {Group} from "../../model/group/group";
import {GroupAdminService} from "../../services/group.service";
import {Utils} from "../../../commons/util/utils";
import {ChildGeneral} from "../../menu/model/children/child-general";
import {CommonMessages} from "../../../commons/util/common-messages";

@Component({
  selector: 'group-list-manage',
  templateUrl: './group-list-manage.component.html',
  providers: [GroupAdminService, ChildrenAdminService]
})
export class GroupListManageComponent implements OnInit{
    constructor(
        private groupAdminService: GroupAdminService,
        private childrenAdminService: ChildrenAdminService,
        private router: Router,
        private route: ActivatedRoute){}
  
    private groupId: number;
    private group: Group;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isSaving = false;
    private items: MenuItem[];
    
    private currentChildrenList: any[] = [];
    private grouplessChildrenList: any[] = [];
    
    ngOnInit(){
        this.groupId = this.route.snapshot.params['groupId'];
        this.items = BreadMaker.makeBreadcrumbs("Zarządzanie","Grupy","Podgląd","Lista dzieci");
        
        this.getGroup();
        this.getGrouplessChildrenList();
    }
    
    getGroup(){
        this.isLoading = true;
        this.groupAdminService.getGroup(this.groupId)
            .subscribe( data => {
                this.group = data;
                this.msgs = [];
                this.currentChildrenList = data.children;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
            });
    }
    
    getGrouplessChildrenList(){
        this.childrenAdminService.getChildrenGrouplessMinimal()
            .subscribe( data => {
                this.grouplessChildrenList = data;
                this.msgs = [];
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            })
    }
    
    updateChildrenList(){
        this.isSaving = true;
        this.group.children = this.currentChildrenList;
        this.groupAdminService.updateGroup(this.group)
            .subscribe( data => {
                this.msgs = CommonMessages.groupChildListSuccess();
                this.isSaving = false;
            }, err => {
                this.msgs = CommonMessages.groupChildListError()
                this.isSaving = false;
            });
    }
    
    routeBackToDetails(){
        this.router.navigate(['/admin/group/detail', this.groupId]);
    }
    
}
