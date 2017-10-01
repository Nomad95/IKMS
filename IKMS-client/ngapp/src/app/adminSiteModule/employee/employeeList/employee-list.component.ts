import { Component, OnInit } from '@angular/core';
import { EmployeeAdminService } from "../../services/employee-admin.service";
import { EmployeeGeneral } from "../../menu/model/employee/employee-general";
import { Page } from "../../../commons/model/page";
import { Router } from "@angular/router";

@Component({
  selector: 'employee-list',
  templateUrl: './employee-list.component.html',
  providers: [EmployeeAdminService]
})
export class EmployeeListComponent implements OnInit{
    constructor(
        private employeeAdminService: EmployeeAdminService,
        private router: Router){}
    
    private page: number = 0;
    private size: number = 20;
    private employees: EmployeeGeneral[];
    private currentPageData: Page;
    
    ngOnInit(){
        this.employeeAdminService.getEmployeeGeneralDetails(this.size,this.page)
        .subscribe( data => {
            this.currentPageData = data;
            this.employees = data.content;
            this.page = data.number;
        }, err => console.log(err));
    }
    
    loadNewPage(event){
        this.employeeAdminService.getEmployeeGeneralDetails(this.size,event.page)
        .subscribe( data => {
            this.currentPageData = data;
            this.employees = data.content;
            this.page = data.number;
        }, err => console.log(err));
    }
  
    navigateToEmployeeDetails(employeeId, personalDataId){
      this.router.navigate(['/admin/employee', employeeId], { queryParams: {personalDataId: personalDataId}});
        console.log(employeeId, personalDataId);
    }
}
