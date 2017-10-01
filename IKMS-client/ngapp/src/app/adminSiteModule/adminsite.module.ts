import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminSiteComponent } from "./adminsite.component";
import { AdminSiteRoutingModule } from "./adminsite-routing.module";
import { AdminSidebar } from "./menu/sidebar/admin-sidebar";
import { EmployeeListComponent } from "./employee/employeeList/employee-list.component";
import { EmployeeDetailComponent } from "./employee/employeeDetail/employee-detail.component";

import { HttpModule } from "@angular/http";
import { PanelMenuModule } from "../../../node_modules/primeng/components/panelmenu/panelmenu";
import { ButtonModule } from "../../../node_modules/primeng/components/button/button";
import { TabViewModule } from "../../../node_modules/primeng/components/tabview/tabview";
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MegaMenuModule } from '../../../node_modules/primeng/components/megamenu/megamenu';
import { DataTableModule } from "../../../node_modules/primeng/components/datatable/datatable";
import { PaginatorModule } from "../../../node_modules/primeng/components/paginator/paginator";

@NgModule({
  imports: [
    CommonModule,
    AdminSiteRoutingModule,
    HttpModule,
    PanelMenuModule,
    DataTableModule,
    ButtonModule,
    TabViewModule,
    CodeHighlighterModule,
    MegaMenuModule,
    PaginatorModule
  ],
  declarations: [
    AdminSiteComponent,
    AdminSidebar,
    EmployeeListComponent,
    EmployeeDetailComponent
  ]
})
export class AdminSiteModule { }
