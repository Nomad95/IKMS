import { Component, OnInit } from '@angular/core';
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {CommonMessages} from "../../../commons/util/common-messages";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenEmployeeService} from "../../services/children-employee.service";
import {ChildGeneral} from "../../model/children/child-general";
import {ParentEmployeeService} from "../../services/parent-employee.service";

@Component({
  selector: 'children-list',
  templateUrl: './children-list.component.html',
  providers: [ChildrenEmployeeService, ConfirmationService, ParentEmployeeService]
})
export class ChildrenListComponent implements OnInit{
    constructor(
        private childrenEmployeeService: ChildrenEmployeeService,
        private confirmationService: ConfirmationService,
        private parentEmployeeService: ParentEmployeeService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private children: ChildGeneral[];
    private currentPageData: Page;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isNavigating = false;
    private items: MenuItem[];
    
    ngOnInit(){
        this.loadChildren(this.size,this.page);
        this.items = BreadMaker.makeBreadcrumbs("Dzieci","Lista dzieci");
    }
    
    loadNewPage(event){
        this.loadChildren(this.size, event.page);
    }
    
    loadChildren(size, page){
        this.isLoading = true;
        this.childrenEmployeeService.getChildrenGeneralDetails(size, page)
        .subscribe( data => {
            this.currentPageData = data;
            this.children = data.content;
            this.page = data.number;
            this.isLoading = false;
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isLoading = false;
        });
    }
    
    navigateToChildDetails(childId, personalDataId){
      this.router.navigate(['/employee/child', childId], { queryParams: {personalDataId: personalDataId}});
    }
    
    navigateToParentDetails(parentId){
        this.isNavigating = true;
        this.parentEmployeeService.getParent(parentId).subscribe( data => {
            console.log("not implemented yet");//todo
            this.router.navigate(['/admin/parent', parentId], { queryParams: {personalDataId: data.personalData.id}});
            this.isNavigating = false;
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isNavigating = false;
        });
    }
    
    delete(childId){
        this.confirmationService.confirm({
            message: 'Czy napewno chcesz usunąć to dziecko? Wszystkie związane z nim dane, zostaną usunięte.',
            header: 'Potwierdzenie usunięcia',
            accept: () => {
                this.childrenEmployeeService.deleteChild(childId)
                    .subscribe( data =>{
                        this.loadChildren(this.size,this.page);
                        this.msgs = [];
                    }, err => this.msgs = CommonMessages.childDeletingError());
            },
            reject: () => {}
        });
    }
}
