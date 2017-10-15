import { Component, OnInit } from '@angular/core';
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ParentGeneral} from "../../menu/model/parent/parent-general";
import {ParentAdminService} from "../../services/parent-admin.service";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {CommonMessages} from "../../../commons/util/common-messages";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";

@Component({
  selector: 'parent-list',
  templateUrl: './parent-list.component.html',
  providers: [ParentAdminService, ConfirmationService]
})
export class ParentListComponent implements OnInit{
  constructor(
    private parentAdminService: ParentAdminService,
    private confirmationService: ConfirmationService,
    private router: Router){}

  private page: number = 0;
  private size: number = 20;
  private parents: ParentGeneral[];
  private currentPageData: Page;
  private msgs: Message[] = [];
  private isLoading: boolean = true;
  private items: MenuItem[];

  ngOnInit(){
   this.loadParents(this.size, this.page);
   this.items = BreadMaker.makeBreadcrumbs("Rodzice", "Lista rodziców");
  }

  loadNewPage(event){
    this.loadParents(this.size, event.page);
  }

  loadParents(size, page){
    this.isLoading = true;
    this.parentAdminService.getParentGeneralDetails(size, page)
      .subscribe( data => {
        this.currentPageData = data;
        this.parents = data.content;
        this.page = data.number;
        this.isLoading = false;
      }, err => {
        this.msgs = ErrorHandler.handleGenericServerError(err);
        this.isLoading = false;
      });
  }

  navigateToParentDetails(parentId, personalDataId){
    this.router.navigate(['/admin/parent', parentId], { queryParams: {personalDataId: personalDataId}});
  }

  delete(parentId){
    this.confirmationService.confirm({
      message: 'Czy napewno chcesz usunąć tego rodzica? Wszystkie związane z nim dane, zostaną usunięte.',
      header: 'Potwierdzenie usunięcia',
      accept: () => {
        this.parentAdminService.deleteParent(parentId)
          .subscribe( data =>{
            this.loadParents(this.size,this.page);
            this.msgs = [];
          }, err => this.msgs = CommonMessages.employeeDeletingError());
      },
      reject: () => {}
    });
  }
}
