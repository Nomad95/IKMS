import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeeSiteComponent } from "./employeesite.component";
import { EmployeeSiteRoutingModule } from "./employeesite-routing.module";
import { EmployeeSidebar } from "./menu/sidebar/employee-sidebar";

import { PanelMenuModule } from "../../../node_modules/primeng/components/panelmenu/panelmenu";
import { ButtonModule } from "../../../node_modules/primeng/components/button/button";
import { TabViewModule } from "../../../node_modules/primeng/components/tabview/tabview";
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MegaMenuModule } from '../../../node_modules/primeng/components/megamenu/megamenu';

@NgModule({
  imports: [
    CommonModule,
    EmployeeSiteRoutingModule,
    PanelMenuModule,
    ButtonModule,
    TabViewModule,
    CodeHighlighterModule,
    MegaMenuModule
  ],
  declarations: [
    EmployeeSiteComponent,
    EmployeeSidebar
  ]
})
export class EmployeeSiteModule { }
