import { Component, OnInit } from '@angular/core';
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {Group} from "../../model/group/group";
import {GroupService} from "../../../sharedModule/services/group.service";

@Component({
  selector: 'group-list',
  templateUrl: './group-list.component.html',
  providers: [ConfirmationService]
})
export class GroupListComponent implements OnInit{
    constructor(
        private groupService: GroupService,
        private confirmationService: ConfirmationService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private groups: Group[];
    private currentPageData: Page;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isNavigating = false;
    private items: MenuItem[];
    
    ngOnInit(){
        this.loadGroups(this.size,this.page);
        this.items = BreadMaker.makeBreadcrumbs("Zarządzanie","Grupy");
    }
    
    loadNewPage(event){
        this.loadGroups(this.size, event.page);
    }
    
    loadGroups(size, page){
        this.isLoading = true;
        this.groupService.getGroupList(size, page)
        .subscribe( data => {
            this.currentPageData = data;
            this.groups = data.content;
            this.page = data.number;
            this.isLoading = false;
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isLoading = false;
        });
    }
    
    navigateToGroupDetails(groupId){
      this.router.navigate(['/employee/group/detail', groupId]);
    }
    
}
