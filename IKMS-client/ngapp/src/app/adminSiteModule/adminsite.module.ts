import {
  NgModule, CommonModule, ChildrenCreateComponent,
  AdminSiteComponent, AdminSiteRoutingModule, AdminSidebar,
  EmployeeListComponent, EmployeeDetailComponent, EmployeeEditComponent,
  PersonalDataEditComponent, AddressEditComponent, AddressCreateComponent,
  ParentListComponent, ParentDetailComponent, ParentEditComponent,
  ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent, RegistrationComponent,
  AddressRegistrationComponent
} from './index';

import {AuthGuard, LoginService} from './index';

import {
  HttpModule, FormsModule, PanelMenuModule,
  ButtonModule, TabViewModule, CodeHighlighterModule,
  MegaMenuModule, DataTableModule, PaginatorModule,
  PanelModule, InputTextModule, DialogModule,
  MessagesModule, DropdownModule, InputMaskModule,
  CalendarModule, ConfirmDialogModule, GrowlModule,
  BreadcrumbModule, TooltipModule, InputTextareaModule, SharedModule
} from './index';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    AdminSiteRoutingModule,
    HttpModule,
    SharedModule,
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
    RegistrationComponent,
    AddressRegistrationComponent
  ],
  providers: [
    AuthGuard,
    LoginService
  ]
})
export class AdminSiteModule {
}
