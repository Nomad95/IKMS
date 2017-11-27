import {
    NgModule, CommonModule, EmployeeSiteComponent,
    EmployeeSiteRoutingModule, EmployeeSidebar,
    FormsModule, HttpModule, SharedModule
} from './index';

import {AuthGuard} from "./index";

import {LoginService} from "./index";

import {
    PanelMenuModule, ButtonModule, TabViewModule,
    CodeHighlighterModule, MegaMenuModule, DataTableModule,
    PaginatorModule, PanelModule, InputTextModule,
    DialogModule, MessagesModule, DropdownModule,
    InputMaskModule, CalendarModule, ConfirmDialogModule,
    GrowlModule, BreadcrumbModule, TooltipModule
} from './index';
import {
    AddressEditComponent,
    PersonalDataEditComponent, AddressCreateComponent,
    ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent,
    NgbModule
} from './index';
import {SchedulesModule} from "../scheduleModule/schedule.module";
import {GroupScheduleComponent} from "./schedules/schedule/group-schedule.component";
import {ChildScheduleComponent} from "./schedules/schedule/child-schedule.component";
import {EmployeeScheduleComponent} from "./schedules/schedule/employee-schedule.component";
import {CollectiveScheduleComponent} from "./schedules/schedule/collective-schedule.component";

@NgModule({
    imports: [
        CommonModule,
        HttpModule,
        SharedModule,
        EmployeeSiteRoutingModule,
        PanelMenuModule,
        ButtonModule,
        TabViewModule,
        CodeHighlighterModule,
        MegaMenuModule,
        DataTableModule,
        MessagesModule,
        InputTextModule,
        DropdownModule,
        PaginatorModule,
        InputMaskModule,
        PanelModule,
        DialogModule,
        CalendarModule,
        ConfirmDialogModule,
        GrowlModule,
        BreadcrumbModule,
        TooltipModule,
        FormsModule,
        NgbModule.forRoot(),
            SchedulesModule
    ],
    declarations: [
        EmployeeSiteComponent,
        EmployeeSidebar,
        AddressEditComponent,
        PersonalDataEditComponent,
        AddressCreateComponent,
        ChildrenListComponent,
        ChildrenDetailComponent,
        ChildrenEditComponent,
        CollectiveScheduleComponent,
        EmployeeScheduleComponent,
        ChildScheduleComponent,
        GroupScheduleComponent
    ],
    providers: [
        AuthGuard,
        LoginService
    ]
})
export class EmployeeSiteModule { }
