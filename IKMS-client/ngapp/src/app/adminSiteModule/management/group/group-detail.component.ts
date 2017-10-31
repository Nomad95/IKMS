import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenService} from "../../../sharedModule/services/children.service";
import {Group} from "../../model/group/group";
import {Utils} from "../../../commons/util/utils";
import {ChildGeneral} from "../../menu/model/children/child-general";
import {GroupService} from "../../../sharedModule/services/group.service";

@Component({
  selector: 'group-detail',
  templateUrl: './group-detail.component.html',
  providers: []
})
export class GroupDetailComponent implements OnInit{
    constructor(
        private groupService: GroupService,
        private childrenService: ChildrenService,
        private router: Router,
        private route: ActivatedRoute){}
  
    private groupId: number;
    private group: Group;
    private children: ChildGeneral[];
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];
    
    private displayGroupEditModal = false;
    private displayPersonalDataEditModal = false;
    private displayAddressEditModal = false;
    
    private displayAddressCreateModal = false;
    
    ngOnInit(){
        this.groupId = this.route.snapshot.params['id'];
        this.items = BreadMaker.makeBreadcrumbs("Zarządzanie","Grupy","Podgląd");
        
        this.getGroup();
    }
    
    getGroup(){
        this.isLoading = true;
        this.groupService.getGroup(this.groupId)
            .subscribe( data => {
                this.group = data;
                this.msgs = [];
                this.getChildren(data.children);
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
            });
    }
    
    getChildren(children){
        this.childrenService.getChildrenGeneralDetailsByIds(Utils.minimalToIdList(children))
            .subscribe( data => {
                this.children = data;
                this.msgs = [];
                this.isLoading = false;
            }, err => {
                this.msgs = ErrorHandler.handleGenericServerError(err);
                this.isLoading = false;
            })
    }
    
    navigateToChildDetails(child){
        this.router.navigate(['/admin/child/detail', child.id], { queryParams: {personalDataId: child.personalData.id}});
    }
    
    showGroupEditModal(): void{
        this.displayGroupEditModal = true;
    }
    
    handleModalClose(value): void{
        this.displayGroupEditModal = value;
        this.displayPersonalDataEditModal = value;
        this.displayAddressEditModal = value;
        this.displayAddressCreateModal = value;
    }
    
    handleGroupUpdate(value): void{
        this.group = value;
        this.displayGroupEditModal = false;
    }
}
