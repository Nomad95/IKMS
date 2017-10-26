import {Component} from '@angular/core';
import {NavigationEnd, NavigationStart, Router} from '@angular/router';
import {Credentials} from './model/credentials-model';
import {LoginService} from './service/login.service';
import {Message} from '../../../node_modules/primeng/components/common/api';
import {ErrorHandler} from '../commons/util/error-handler';
import {CommonMessages} from '../commons/util/common-messages';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    providers: [LoginService]
})
export class LoginComponent {
    constructor(public router: Router,
                public loginService: LoginService) {
    };
    
    private credentials: Credentials = new Credentials();
    private messages: Message[] = [];
    private isLoading = false;
    private isNavigating = false;
    
    private login(credentials: Credentials): void {
        this.isLoading = true;
        
        this.loginService.login(credentials).subscribe(response => {
            this.messages = [];
            this.loginService.getRoleFromToken()
            .subscribe(data => {
                this.forwardToSiteByRole(data);
                this.isLoading = false;
            });
        }, err => {
            this.messages = ErrorHandler.handleLoginError(err);
            this.isLoading = false;
        });
    }
    
    private forwardToSiteByRole(data: any) {
        this.onLazyNavigationStart();
        let role = data.role;
        switch (role) {
            case 'ROLE_ADMIN':
                this.router.navigate(['/admin']);
                break;
            case 'ROLE_EMPLOYEE':
                this.router.navigate(['/employee']);
                break;
            case 'ROLE_PARENT':
                this.router.navigate(['/parent']);
                break;
            default:
                this.messages = CommonMessages.roleFetchingWentWrong();
                break;
        }
        this.onLazyNavigationEnd();
    }
    
    onLazyNavigationStart() {
        this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                this.isNavigating = true;
            }
        });
    }
    
    onLazyNavigationEnd() {
        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.isNavigating = false;
            }
        });
    }
}
