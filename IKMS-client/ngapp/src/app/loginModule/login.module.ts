import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import {HttpModule} from "@angular/http";

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';

import {
  InputTextModule, ButtonModule,
  CodeHighlighterModule, MessagesModule,
  CheckboxModule, PasswordModule } from './index';


@NgModule({
  imports: [
    CommonModule,
    InputTextModule,
    ButtonModule,
    CodeHighlighterModule,
    LoginRoutingModule,
    FormsModule,
    HttpModule,
    MessagesModule,
    CheckboxModule,
    PasswordModule
  ],
  declarations: [LoginComponent]
})
export class LoginModule {
}
