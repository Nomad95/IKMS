import {
    NgModule, CommonModule, ChildrenCreateComponent,
    AdminSiteComponent, AdminSiteRoutingModule, AdminSidebar,
    EmployeeListComponent, EmployeeDetailComponent, EmployeeEditComponent,
    PersonalDataEditComponent, AddressEditComponent, AddressCreateComponent,
    ParentListComponent, ParentDetailComponent, ParentEditComponent,
    ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent,
    RegistrationComponent, AddressRegistrationComponent, GroupListComponent,
    GroupDetailComponent, GroupEditComponent, GroupListManageComponent } from './index';

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
    NgbModule } from './index';
import {GroupCreateComponent} from "./management/groupCreate/group-create.component";


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
        NgbModule.forRoot()
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
        GroupCreateComponent
        
    ],
    providers: [
        AuthGuard,
        LoginService
    ]
})
export class AdminSiteModule {
}
