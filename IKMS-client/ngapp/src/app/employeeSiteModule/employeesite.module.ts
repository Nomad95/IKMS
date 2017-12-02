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
import {EmployeeListComponent} from "./employee/employeeList/employee-list.component";
import {ParentListComponent} from "./parent/parentList/parent-list.component";
import {ParentDetailComponent} from "./parent/parentDetails/parent-detail.component";
import {GroupListComponent} from "./management/group/group-list.component";
import {GroupListManageComponent} from "./management/group/group-list-manage.component";
import {GroupDetailComponent} from "./management/group/group-detail.component";
import {CheckboxModule, DataListModule, PickListModule} from "primeng/primeng";
import {NotificationComponent} from "../sharedModule/notification/notificiationList/notification-list.component";

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
        PickListModule,
        DataListModule,
        CheckboxModule,
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
        GroupScheduleComponent,
        EmployeeListComponent,
        ParentListComponent,
        ParentDetailComponent,
        GroupListComponent,
        GroupListManageComponent,
        GroupDetailComponent,
        NotificationComponent
    ],
    providers: [
        AuthGuard,
        LoginService
    ]
})
export class EmployeeSiteModule { }
