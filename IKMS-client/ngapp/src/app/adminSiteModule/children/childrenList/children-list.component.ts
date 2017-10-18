import { Component, OnInit } from '@angular/core';
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {CommonMessages} from "../../../commons/util/common-messages";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {ChildrenAdminService} from "../../services/children-admin.service";
import {ChildGeneral} from "../../menu/model/children/child-general";

@Component({
  selector: 'children-list',
  templateUrl: './children-list.component.html',
  providers: [ChildrenAdminService, ConfirmationService]
})
export class ChildrenListComponent implements OnInit{
    constructor(
        private childrenAdminService: ChildrenAdminService,
        private confirmationService: ConfirmationService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private children: ChildGeneral[];
    private currentPageData: Page;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
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
        this.childrenAdminService.getChildrenGeneralDetails(size, page)
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
      this.router.navigate(['/admin/child', childId], { queryParams: {personalDataId: personalDataId}});
    }
    
    delete(childId){
        this.confirmationService.confirm({
            message: 'Czy napewno chcesz usunąć to dziecko? Wszystkie związane z nim dane, zostaną usunięte.',
            header: 'Potwierdzenie usunięcia',
            accept: () => {
                this.childrenAdminService.deleteChild(childId)
                    .subscribe( data =>{
                        this.loadChildren(this.size,this.page);
                        this.msgs = [];
                    }, err => this.msgs = CommonMessages.childDeletingError());
            },
            reject: () => {}
        });
    }
}
