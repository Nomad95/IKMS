import { Component, OnInit } from '@angular/core';
import { EmployeeService } from "../../../sharedModule/services/employee.service";
import { EmployeeGeneral } from "../../model/employee/employee-general";
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";
import {ConfirmationService, MenuItem, Message} from "primeng/primeng";
import {ErrorHandler} from "../../../commons/util/error-handler";
import {BreadMaker} from "../../../commons/util/bread-maker";

@Component({
  selector: 'employee-list',
  templateUrl: './employee-list.component.html',
  providers: [ConfirmationService]
})
export class EmployeeListComponent implements OnInit{
    constructor(
        private employeeService: EmployeeService,
        private confirmationService: ConfirmationService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private employees: EmployeeGeneral[];
    private currentPageData: Page;
    private msgs: Message[] = [];
    private isLoading: boolean = true;
    private items: MenuItem[];
    private recipientUsername = '';
    private displayMessage = false;
    
    ngOnInit(){
        this.loadEmployees(this.size,this.page);
        this.items = BreadMaker.makeBreadcrumbs("Pracownicy","Lista pracowników");
    }
    
    loadNewPage(event){
        this.loadEmployees(this.size, event.page);
    }
    
    loadEmployees(size, page){
        this.isLoading = true;
        this.employeeService.getEmployeeGeneralDetails(size, page)
        .subscribe( data => {
            this.currentPageData = data;
            this.employees = data.content;
            this.page = data.number;
            this.isLoading = false;
        }, err => {
            this.msgs = ErrorHandler.handleGenericServerError(err);
            this.isLoading = false;
        });
    }
    
    showMessageBox(username){
        this.recipientUsername = username;
        this.displayMessage = true;
    }
}
