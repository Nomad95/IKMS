import { HttpModule } from "@angular/http";
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParentSiteComponent } from "./parentsite.component";
import { ParentSiteRoutingModule } from "./parentsite-routing.module";
import { ParentSidebar } from "./menu/sidebar/parent-sidebar";

import { PanelMenuModule } from "../../../node_modules/primeng/components/panelmenu/panelmenu";
import { ButtonModule } from "../../../node_modules/primeng/components/button/button";
import { TabViewModule } from "../../../node_modules/primeng/components/tabview/tabview";
import { CodeHighlighterModule } from '../../../node_modules/primeng/components/codehighlighter/codehighlighter';
import { MegaMenuModule } from '../../../node_modules/primeng/components/megamenu/megamenu';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {SharedModule} from "../sharedModule/shared-module.module";
import {CommunicationModule} from "../communicationModule/communication.module";
import {AuthGuard} from "../commons/guards/auth-guard";
import {LoginService} from "../loginModule/service/login.service";
import {
    BreadcrumbModule,
    CalendarModule, CheckboxModule, ConfirmDialogModule, DataListModule,
    DataTableModule, DialogModule, DropdownModule, GrowlModule, InputTextareaModule, InputTextModule, MenubarModule,
    MessagesModule,
    PaginatorModule, PanelModule, PickListModule, ScheduleModule, TooltipModule, TreeModule
} from "primeng/primeng";
import {FormsModule} from "@angular/forms";
import {SchedulesModule} from "../scheduleModule/schedule.module";
import {DidacticMaterialsComponent} from "./files/didactic/didactic-materials.component";
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";


@NgModule({
    imports: [
        CommonModule,
        HttpModule,
        ParentSiteRoutingModule,
        PanelMenuModule,
        ButtonModule,
        TabViewModule,
        CodeHighlighterModule,
        MegaMenuModule,
        NgbModule.forRoot(),
        SharedModule,
        CommunicationModule,
    
        FormsModule,
        DataTableModule,
        MessagesModule,
        InputTextModule,
        DropdownModule,
        PaginatorModule,
        DialogModule,
        PanelModule,
        CalendarModule,
        ConfirmDialogModule,
        GrowlModule,
        BreadcrumbModule,
        TooltipModule,
        InputTextareaModule,
        PickListModule,
        MenubarModule,
        ScheduleModule,
        SchedulesModule,
        DataListModule,
        CheckboxModule,
        TreeModule,
    ],
    declarations: [
        ParentSiteComponent,
        ParentSidebar,
        DidacticMaterialsComponent,
        EmployeeListComponent
    ],
    providers: [
        AuthGuard,
        LoginService
    ]
})
export class ParentSiteModule {
}
