import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from './model/credentials-model';
import { LoginService } from './service/login.service';
import { Message } from '../../../node_modules/primeng/components/common/api';
import { ErrorHandler } from '../commons/util/error-handler';
import { CommonMessages } from '../commons/util/common-messages';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  providers: [LoginService]
})
export class LoginComponent  {
    constructor(
        public router: Router,
        public loginService: LoginService) {
    };

  private credentials: Credentials = new Credentials();
  private messages: Message[] = [];
  private isLoading = false;

  private login(credentials: Credentials): void{
      this.isLoading = true;

      this.loginService.login(credentials).subscribe( response => {
          this.messages = [];
          this.loginService.getRole()
              .subscribe( data => {
                  this.forwardToSiteByRole(data);
                  this.isLoading = false;
              });
      }, err =>{
          this.messages = ErrorHandler.handleLoginError(err);
          this.isLoading = false;
      });
  }

  private forwardToSiteByRole(data: any){
      let role = data.role;
      switch (role){
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
  }

}
