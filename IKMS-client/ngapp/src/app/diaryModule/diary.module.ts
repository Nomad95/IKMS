import {NgModule} from '@angular/core';
import {
    BreadcrumbModule,
    ButtonModule,
    CalendarModule, CheckboxModule, CodeHighlighterModule, ConfirmDialogModule, DataTableModule, DialogModule,
    DropdownModule,
    GrowlModule,
    InputMaskModule, InputTextModule,
    MessagesModule, MultiSelectModule,
    ScheduleModule, TooltipModule
} from "primeng/primeng";
import {SharedModule} from "../sharedModule/shared-module.module";
import {HttpModule} from "@angular/http";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {DiaryActivityDetailComponent} from "./diary-activity-detail.component";
import {CommunicationModule} from "../communicationModule/communication.module";
import {DiaryRegistryModal} from "./diary-registry-modal.component";
import {DiaryPreviewComponent} from "./diary-preview.component";


@NgModule({
    declarations: [
        DiaryActivityDetailComponent,
        DiaryRegistryModal,
        DiaryPreviewComponent
    ],
    imports: [
        ScheduleModule,
        SharedModule,
        CommonModule,
        HttpModule,
        SharedModule,
        ButtonModule,
        CodeHighlighterModule,
        MessagesModule,
        InputTextModule,
        DropdownModule,
        InputMaskModule,
        DialogModule,
        CalendarModule,
        ConfirmDialogModule,
        GrowlModule,
        TooltipModule,
        FormsModule,
        MultiSelectModule,
        BreadcrumbModule,
        DataTableModule,
        CheckboxModule,
        CommunicationModule
    ],
    providers: [],
    exports: [
    ],
    bootstrap: []
})
export class DiaryModule {
}
