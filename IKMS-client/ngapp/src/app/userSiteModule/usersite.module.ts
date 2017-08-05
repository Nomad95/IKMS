import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserSiteComponent} from "./usersite.component";
import {UserSiteRoutingModule} from "./usersite-routing.module";

@NgModule({
  imports: [
    CommonModule,
    UserSiteRoutingModule
  ],
  declarations: [UserSiteComponent
  ]
})
export class UserSiteModule { }
