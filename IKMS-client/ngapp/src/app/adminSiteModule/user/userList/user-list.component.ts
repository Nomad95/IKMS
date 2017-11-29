import { Component, OnInit } from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import { EmployeeGeneral } from "../../model/employee/employee-general";
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {CommonMessages} from "../../../commons/util/common-messages";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";
import {User} from "../../model/user/user";
import {UserService} from "../../../sharedModule/services/user.service";
import {MinimalDto} from "../../model/minimal-dto";

@Component({
  selector: 'user-list',
  templateUrl: './user-list.component.html',
  providers: [ConfirmationService]
})
export class UserListComponent implements OnInit{
    constructor(
        private userService: UserService,
        private confirmationService: ConfirmationService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private users: User[];
    private currentPageData: Page;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];
    private currentUser: MinimalDto;
    
    ngOnInit(){
        this.loadUsers(this.size,this.page);
        this.getCurrentUser();
        this.items = BreadMaker.makeBreadcrumbs("Użytkownicy","Lista użytkowników");
    }
    
    getCurrentUser(){
        this.userService.getCurrentUser()
            .subscribe( data => {
                this.currentUser = data;
            }, err => this.msgs = ErrorHandler.handleGenericServerError(err))
    }
    
    loadNewPage(event){
        this.loadUsers(this.size, event.page);
    }
    
    loadUsers(size, page){
        this.isLoading = true;
        this.userService.getUsers(size, page)
        .subscribe( data => {
            this.currentPageData = data;
            console.log(data.content);
            this.users = data.content;
            this.page = data.number;
            this.isLoading = false;
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isLoading = false;
        });
    }
    
    switchEnabled(value, rowData){
        if(rowData.id == this.currentUser.id)
            return;
        rowData.enabled = value;
        console.log(rowData);
        this.userService.updateUser(rowData)
            .subscribe( data => {
               console.log(data);
            });
    }
    
    delete(employeeId){
        this.confirmationService.confirm({
            message: 'Czy napewno chcesz usunąć konto tego użytkownika?',
            header: 'Potwierdzenie usunięcia',
            accept: () => {
                this.userService.deleteUser(employeeId)
                    .subscribe( data =>{
                        this.loadUsers(this.size,this.page);
                        this.msgs = [];
                    }, err => this.msgs = CommonMessages.userDeletingError());
            },
            reject: () => {}
        });
    }
}
