import { Component, OnInit } from '@angular/core';
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {CommonMessages} from "../../../commons/util/common-messages";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {Group} from "../../model/group/group";
import {GroupService} from "../../../sharedModule/services/group.service";
import {ClassroomService} from "../../../sharedModule/services/classroom.service";
import {Classroom} from "../../model/classroom/classroom";

@Component({
  selector: 'classroom-list',
  templateUrl: './classroom-list.component.html',
  providers: [ConfirmationService]
})
export class ClassroomListComponent implements OnInit{
    constructor(
        private classroomService: ClassroomService,
        private confirmationService: ConfirmationService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private classrooms: Classroom[];
    private currentPageData: Page;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private isNavigating = false;
    private items: MenuItem[];
    
    ngOnInit(){
        this.loadClassrooms(this.size,this.page);
        this.items = BreadMaker.makeBreadcrumbs("Zarządzanie","Sale");
    }
    
    loadNewPage(event){
        this.loadClassrooms(this.size, event.page);
    }
    
    loadClassrooms(size, page){
        this.isLoading = true;
        this.classroomService.getClassroomList(size, page)
        .subscribe( data => {
            this.currentPageData = data;
            this.classrooms = data.content;
            this.page = data.number;
            this.isLoading = false;
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isLoading = false;
        });
    }
    
    delete(classroomId){
        this.confirmationService.confirm({
            message: 'Czy napewno chcesz usunąć tą salę? Wszystkie związane z nim dane, zostaną usunięte.',
            header: 'Potwierdzenie usunięcia',
            accept: () => {
                this.classroomService.deleteClassroom(classroomId)
                    .subscribe( data =>{
                        this.loadClassrooms(this.size,this.page);
                        this.msgs = CommonMessages.classroomDeletingSuccess(data.name);
                    }, err => {
                        this.msgs = CommonMessages.classroomDeletingError();
                    });
            },
            reject: () => {}
        });
    }
}
