import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from './model/credentials-model';
import { LoginService } from './service/login.service';
import { Message } from '../../../node_modules/primeng/components/common/api';
import {ErrorHandler} from "../commons/util/error-handler";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  providers: [LoginService]
})
export class LoginComponent  {
    constructor(
        public router: Router,
        public loginService: LoginService) {
    }

  private credentials: Credentials = new Credentials();
  private messages: Message[] = [];

  private login(credentials: Credentials): void{
      this.loginService.login(credentials).subscribe( response =>{
          this.messages = [];
      }, err =>{
          this.messages = ErrorHandler.handleLoginError(err);
      });
      //TODO: przekieruj na stronke
  }

}
