import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule }   from '@angular/forms';
import {HttpModule} from "@angular/http";

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';

import { InputTextModule } from '../../../node_modules/primeng/components/inputtext/inputtext';
import { ButtonModule } from '../../../node_modules/primeng/components/button/button';
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MessagesModule } from '../../../node_modules/primeng/components/messages/messages';


@NgModule({
  imports: [
    CommonModule,
    InputTextModule,
    ButtonModule,
    CodeHighlighterModule,
    LoginRoutingModule,
    FormsModule,
    HttpModule,
    MessagesModule
  ],
  declarations: [LoginComponent]
})
export class LoginModule {//TODO: add image to assets
}
