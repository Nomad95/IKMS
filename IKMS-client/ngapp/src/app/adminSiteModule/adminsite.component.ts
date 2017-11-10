import {Component} from '@angular/core';
import {LoginService} from "../loginModule/service/login.service";

@Component({
  selector: 'admin-site',
  templateUrl: './adminsite.component.html',
    providers: [LoginService]
})
export class AdminSiteComponent {
    constructor(private loginService: LoginService){}
    
    logout(){
        this.loginService.logout();
    }
}
