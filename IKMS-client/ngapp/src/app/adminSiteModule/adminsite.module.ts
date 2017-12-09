import {
    NgModule, CommonModule, ChildrenCreateComponent,
    AdminSiteComponent, AdminSiteRoutingModule, AdminSidebar,
    EmployeeListComponent, EmployeeDetailComponent, EmployeeEditComponent,
    PersonalDataEditComponent, AddressEditComponent, AddressCreateComponent,
    ParentListComponent, ParentDetailComponent, ParentEditComponent,
    ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent,
    RegistrationComponent, AddressRegistrationComponent, GroupListComponent,
    GroupDetailComponent, GroupEditComponent, GroupListManageComponent,
    GroupCreateComponent, CollectiveScheduleComponent } from './index';

import {AuthGuard, LoginService} from './index';

import {
    HttpModule, FormsModule, PanelMenuModule,
    ButtonModule, TabViewModule, CodeHighlighterModule,
    MegaMenuModule, DataTableModule, PaginatorModule,
    PanelModule, InputTextModule, DialogModule,
    MessagesModule, DropdownModule, InputMaskModule,
    CalendarModule, ConfirmDialogModule, GrowlModule,
    BreadcrumbModule, TooltipModule, InputTextareaModule,
    SharedModule, PickListModule, MenubarModule,
    NgbModule, ScheduleModule } from './index';
import {SchedulesModule} from "../scheduleModule/schedule.module";
import {CheckboxModule, DataListModule, FieldsetModule, FileUploadModule, TreeModule} from "primeng/primeng";
import {EmployeeScheduleComponent} from "./schedules/schedule/employee-schedule.component";
import {ChildScheduleComponent} from "./schedules/schedule/child-schedule.component";
import {GroupScheduleComponent} from "./schedules/schedule/group-schedule.component";
import {UserListComponent} from "./user/userList/user-list.component";
import {ClassroomListComponent} from "./management/classroom/classroom-list.component";
import {ClassroomCreateComponent} from "./management/classroom/classroom-create.component";
import {CommunicationModule} from "../communicationModule/communication.module";
import {DidacticMaterialsComponent} from "./files/materials/didactic-materials.component";
import {AddFileComponent} from "./files/materials/add-file.component";


@NgModule({
    imports: [
        CommonModule,
        SharedModule,
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
        GrowlModule,
        BreadcrumbModule,
        TooltipModule,
        InputTextareaModule,
        PickListModule,
        MenubarModule,
        NgbModule.forRoot(),
        ScheduleModule,
        SchedulesModule,
        DataListModule,
        FieldsetModule,
        CheckboxModule,
        CommunicationModule,
        TreeModule,
        FileUploadModule
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
        ParentListComponent,
        ParentDetailComponent,
        ParentEditComponent,
        ChildrenListComponent,
        ChildrenDetailComponent,
        ChildrenEditComponent,
        ChildrenCreateComponent,
        ParentListComponent,
        ParentDetailComponent,
        ParentEditComponent,
        GroupListComponent,
        GroupDetailComponent,
        GroupEditComponent,
        RegistrationComponent,
        AddressRegistrationComponent,
        GroupListManageComponent,
        GroupCreateComponent,
        GroupCreateComponent,
        CollectiveScheduleComponent,
        EmployeeScheduleComponent,
        ChildScheduleComponent,
        GroupScheduleComponent,
        UserListComponent,
        ClassroomListComponent,
        ClassroomCreateComponent,
        DidacticMaterialsComponent,
        AddFileComponent
    ],
    providers: [
        AuthGuard,
        LoginService
    ]
})
export class AdminSiteModule {
}
