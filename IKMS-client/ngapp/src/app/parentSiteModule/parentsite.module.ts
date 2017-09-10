import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParentSiteComponent } from "./parentsite.component";
import { ParentSiteRoutingModule } from "./parentsite-routing.module";

@NgModule({
  imports: [
    CommonModule,
    ParentSiteRoutingModule
  ],
  declarations: [ParentSiteComponent
  ]
})
export class ParentSiteModule { }
