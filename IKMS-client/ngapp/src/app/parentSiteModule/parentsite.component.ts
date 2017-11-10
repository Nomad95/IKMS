import { Component } from '@angular/core';
import {LoginService} from "../loginModule/service/login.service";

@Component({
    selector: 'parent-site',
    templateUrl: './parentsite.component.html',
    providers: [LoginService]
    
})
export class ParentSiteComponent {
    constructor(private loginService: LoginService){}
    
    logout(){
        this.loginService.logout();
    }
}
