import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Credentials } from "./model/credentials-model";
import { LoginService } from "./service/login.service";

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

  private login(credentials: Credentials): void{
    this.loginService.login(credentials).subscribe( response =>{});
    console.log("save: " + JSON.stringify(this.credentials));
    //TODO: przekieruj na stronke
  }

}
