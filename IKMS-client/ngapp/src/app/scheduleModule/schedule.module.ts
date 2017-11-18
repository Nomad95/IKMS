import {NgModule} from '@angular/core';
import {ScheduleComponent} from "./schedule.component";
import {
    ButtonModule,
    CalendarModule, CodeHighlighterModule, ConfirmDialogModule, DialogModule, DropdownModule, GrowlModule,
    InputMaskModule, InputTextModule,
    MessagesModule, MultiSelectModule,
    ScheduleModule, TooltipModule
} from "primeng/primeng";
import {SharedModule} from "../sharedModule/shared-module.module";
import {ActivityCreateComponent} from "./modals/activity-create.component";
import {HttpModule} from "@angular/http";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {ActivityDetailsComponent} from "./modals/activity-details.component";
import {ActivityEditComponent} from "./modals/activity-edit.component";


@NgModule({
    declarations: [
        ScheduleComponent,
        ActivityCreateComponent,
        ActivityDetailsComponent,
        ActivityEditComponent
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
        MultiSelectModule
    ],
    providers: [],
    exports: [
        ScheduleComponent,
        ActivityCreateComponent,
        ActivityDetailsComponent
    ],
    bootstrap: []
})
export class SchedulesModule {
}
