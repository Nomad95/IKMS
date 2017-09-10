import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminSiteComponent } from "./adminsite.component";
import { AdminSiteRoutingModule } from "./adminsite-routing.module";

@NgModule({
  imports: [
    CommonModule,
    AdminSiteRoutingModule
  ],
  declarations: [AdminSiteComponent
  ]
})
export class AdminSiteModule { }
