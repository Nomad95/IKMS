import {
    NgModule, CommonModule, EmployeeSiteComponent,
    EmployeeSiteRoutingModule, EmployeeSidebar,
    FormsModule, HttpModule
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
    PersonalDataEditComponent, EnumTranslatePipe, AddressCreateComponent,
    ChildrenListComponent, ChildrenDetailComponent, ChildrenEditComponent
} from './index';
import {SharedModule} from "../adminSiteModule/sharedModule/shared-module.module";

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
        FormsModule
    ],
    declarations: [
        EmployeeSiteComponent,
        EmployeeSidebar,
        AddressEditComponent,
        PersonalDataEditComponent,
        AddressCreateComponent,
        ChildrenListComponent,
        ChildrenDetailComponent,
        ChildrenEditComponent
    ],
    providers: [
        AuthGuard,
        LoginService
    ]
})
export class EmployeeSiteModule { }
