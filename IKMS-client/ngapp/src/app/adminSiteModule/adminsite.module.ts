import {
    NgModule, CommonModule,
    AdminSiteComponent, AdminSiteRoutingModule, AdminSidebar,
    EmployeeListComponent, EmployeeDetailComponent, EmployeeEditComponent,
    PersonalDataEditComponent, AddressEditComponent, AddressCreateComponent,
    ParentListComponent, ParentDetailComponent, ParentEditComponent,
    ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent} from './index';

import {EnumTranslatePipe} from './index';

import {AuthGuard, LoginService} from './index';

import {
    HttpModule, FormsModule, PanelMenuModule,
    ButtonModule, TabViewModule, CodeHighlighterModule,
    MegaMenuModule, DataTableModule, PaginatorModule,
    PanelModule, InputTextModule, DialogModule,
    MessagesModule, DropdownModule, InputMaskModule,
    CalendarModule, ConfirmDialogModule, GrowlModule,
    BreadcrumbModule, TooltipModule } from './index';


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
        TooltipModule
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
        EnumTranslatePipe,
        ParentListComponent,
        ParentDetailComponent,
        ParentEditComponent,
        ChildrenListComponent,
        ChildrenDetailComponent,
        ChildrenEditComponent,
        ParentListComponent,
        ParentDetailComponent,
        ParentEditComponent,
        EnumTranslatePipe
  ],
    providers: [
            AuthGuard,
            LoginService
    ]
})
export class AdminSiteModule { }
