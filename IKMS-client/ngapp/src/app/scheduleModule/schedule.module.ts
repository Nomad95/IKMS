import {NgModule} from '@angular/core';
import {ScheduleComponent} from "./schedule.component";
import {ScheduleModule} from "primeng/primeng";


@NgModule({
    declarations: [
        ScheduleComponent
    ],
    imports: [
        ScheduleModule
    ],
    providers: [],
    exports: [ScheduleComponent],
    bootstrap: []
})
export class SchedulesModule {
}
