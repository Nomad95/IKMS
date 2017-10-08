import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminSiteComponent } from "./adminsite.component";
import { AdminSiteRoutingModule } from "./adminsite-routing.module";
import { AdminSidebar } from "./menu/sidebar/admin-sidebar";
import { EmployeeListComponent } from "./employee/employeeList/employee-list.component";
import { EmployeeDetailComponent } from "./employee/employeeDetail/employee-detail.component";
import { EmployeeEditComponent } from "./employee/employeeEdit/employee-edit.component";
import { PersonalDataEditComponent } from "./employee/employeeEdit/personal-data-edit.component";
import { AddressEditComponent } from "./employee/employeeEdit/address-edit.component";
import { AddressCreateComponent } from "./employee/employeeEdit/address-create.component";

import { EnumTranslatePipe } from "../commons/pipes/enum-translate";

import { HttpModule } from "@angular/http";
import { FormsModule } from "@angular/forms";
import { PanelMenuModule } from "../../../node_modules/primeng/components/panelmenu/panelmenu";
import { ButtonModule } from "../../../node_modules/primeng/components/button/button";
import { TabViewModule } from "../../../node_modules/primeng/components/tabview/tabview";
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MegaMenuModule } from '../../../node_modules/primeng/components/megamenu/megamenu';
import { DataTableModule } from "../../../node_modules/primeng/components/datatable/datatable";
import { PaginatorModule } from "../../../node_modules/primeng/components/paginator/paginator";
import { PanelModule } from "../../../node_modules/primeng/components/panel/panel";
import { InputTextModule } from "../../../node_modules/primeng/components/inputtext/inputtext";
import { DialogModule } from "../../../node_modules/primeng/components/dialog/dialog";
import { MessagesModule } from "../../../node_modules/primeng/components/messages/messages";
import { DropdownModule } from "../../../node_modules/primeng/components/dropdown/dropdown";
import { InputMaskModule } from "../../../node_modules/primeng/components/inputmask/inputmask";
import { CalendarModule } from "../../../node_modules/primeng/components/calendar/calendar";
import { ConfirmDialogModule } from "../../../node_modules/primeng/components/confirmdialog/confirmdialog";
import { GrowlModule } from "../../../node_modules/primeng/components/growl/growl";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        AdminSiteRoutingModule,
        HttpModule,
        PanelMenuModule,
        DataTableModule,
        ButtonModule,
        MessagesModule,
        TabViewModule,
        InputTextModule,
        CodeHighlighterModule,
        MegaMenuModule,
        DropdownModule,
        PaginatorModule,
        InputMaskModule,
        DialogModule,
        PanelModule,
        CalendarModule,
        ConfirmDialogModule,
        GrowlModule
    ],
    declarations: [
        AdminSiteComponent,
        AdminSidebar,
        EmployeeListComponent,
        EmployeeDetailComponent,
        EmployeeEditComponent,
        AddressEditComponent,
        PersonalDataEditComponent,
        AddressCreateComponent,
        EnumTranslatePipe
  ]
})
export class AdminSiteModule { }
