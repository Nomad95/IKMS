import { Component } from '@angular/core';
import {LoginService} from "../loginModule/service/login.service";

@Component({
    selector: 'employee-site',
    templateUrl: './employeesite.component.html',
    providers: [LoginService]
})
export class EmployeeSiteComponent {
    constructor(private loginService: LoginService){}
    
    logout(){
        this.loginService.logout();
    }
}
