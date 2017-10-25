import {
    NgModule, CommonModule, ChildrenCreateComponent,
    AdminSiteComponent, AdminSiteRoutingModule, AdminSidebar,
    EmployeeListComponent, EmployeeDetailComponent, EmployeeEditComponent,
    PersonalDataEditComponent, AddressEditComponent, AddressCreateComponent,
    ParentListComponent, ParentDetailComponent, ParentEditComponent,
    ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent} from './index';

import {AuthGuard, LoginService} from './index';

import {
    HttpModule, FormsModule, PanelMenuModule,
    ButtonModule, TabViewModule, CodeHighlighterModule,
    MegaMenuModule, DataTableModule, PaginatorModule,
    PanelModule, InputTextModule, DialogModule,
    MessagesModule, DropdownModule, InputMaskModule,
    CalendarModule, ConfirmDialogModule, GrowlModule,
    BreadcrumbModule, TooltipModule, InputTextareaModule } from './index';

import {SharedModule} from "./sharedModule/shared-module.module";

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
        GrowlModule,
        BreadcrumbModule,
        TooltipModule,
        InputTextareaModule
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
  ],
    providers: [
            AuthGuard,
            LoginService
    ]
})
export class AdminSiteModule { }
